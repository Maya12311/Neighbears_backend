package com.example.neighbears.service;

import com.example.neighbears.dto.AddressDTO;
import com.example.neighbears.dto.CustomerDTO;
import com.example.neighbears.dto.RegistrationUserAddressDTO;
import com.example.neighbears.exceptions.EmailAlreadyUsedException;
import com.example.neighbears.model.Address;
import com.example.neighbears.model.Customer;
import com.example.neighbears.repository.AddressRepository;
import com.example.neighbears.repository.CustomerRepository;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class NeighbearsUserDetailsService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    private final Environment environment;
    private final AddressRepository addressRepository;

    public NeighbearsUserDetailsService(PasswordEncoder passwordEncoder, AddressRepository addressRepository, CustomerRepository customerRepository, Environment environment) {
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
        this.environment = environment;
        this.addressRepository = addressRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Customer> optional = customerRepository.findByEmail(username);
        Customer customer = optional.orElseThrow(() ->
                new UsernameNotFoundException("The user wasn't found: " + username));
        //Customer customer =customerRepository.findByEmail(username).orElseThrow(() ->
        //      new UsernameNotFoundException("User details not found for the user" + username));
        List<GrantedAuthority> authorities = customer.getAuthorities().stream().map(authority -> new
                SimpleGrantedAuthority(authority.getName())).collect(Collectors.toList());
        return new User(customer.getEmail(), customer.getPwd(), authorities);
    }


    public CustomerDTO getUserByEmail(String email) throws UsernameNotFoundException {
        System.out.println("was ist das funcking problem");
        Optional<Customer> optional = customerRepository.findByEmail(email);
        Customer customer = optional.orElseThrow(() -> new UsernameNotFoundException("user not found"));
        CustomerDTO customerDTO = new CustomerDTO
                (customer.getId(), customer.getName(), customer.getEmail(), customer.getMobileNumber(), customer.getPwd(), customer.getRole());

        return customerDTO;
    }

    @Transactional
    public Long registerUser(RegistrationUserAddressDTO registrationUserAddressDTO) throws EmailAlreadyUsedException {
        CustomerDTO customerDTO = registrationUserAddressDTO.getCustomerDTO();
        AddressDTO addressDTO = registrationUserAddressDTO.getAddressDTO();

        if(customerRepository.findByEmail(customerDTO.getEmail()).isPresent()){
            throw new EmailAlreadyUsedException(customerDTO.getEmail() + "is already existing");
        };

        Customer customer = new Customer();
        customer.setName(customerDTO.getName());

        customer.setEmail(customerDTO.getEmail());
        customer.setMobileNumber(customerDTO.getMobileNumber());
        customer.setRole(customerDTO.getRole());

            String street = addressDTO.getStreet().trim();
            String house = addressDTO.getHouseNumber().trim();
            String zip = addressDTO.getZipCode().trim();
            String city = addressDTO.getCity().trim();

            Address address = addressRepository.findNormalized(street, house, zip, city)
                    .orElseGet(() -> {
                        Address a = new Address();
                        a.setStreet(street);
                        a.setHouseNumber(house);
                        a.setZipCode(zip);
                        a.setCity(city);
                        return addressRepository.save(a);
                    });
        if (customerDTO.getPwd() == null || customerDTO.getPwd().isBlank()) {
            throw new IllegalArgumentException("Missing password (pwd)");
        }

            String hashPwd = passwordEncoder.encode(customerDTO.getPwd());
            customer.setPwd(hashPwd);
            customer.setAddress(address);

        Customer savedCustomer = customerRepository.save(customer);
        return savedCustomer.getId();

    }
}


