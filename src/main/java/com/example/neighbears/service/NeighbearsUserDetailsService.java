package com.example.neighbears.service;

import com.example.neighbears.dto.*;
import com.example.neighbears.exceptions.NeighbearsException;
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

import java.util.ArrayList;
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
        Optional<Customer> optional = customerRepository.findByEmail(email);
        Customer customer = optional.orElseThrow(() -> new UsernameNotFoundException("user not found"));
        CustomerDTO customerDTO = new CustomerDTO
                (customer.getId(), customer.getName(), customer.getEmail(),
                        customer.getMobileNumber(), customer.getPwd(), customer.getRole());

        return customerDTO;
    }

    public List<CustomerDTO> getAllNeighbears( String email){
System.out.println("BLuuuuuu");
       List<Customer> neighbearsList = new ArrayList<>();
 Optional<Customer> optional = customerRepository.findByEmail(email);
    Customer customer = optional.orElseThrow(() -> new UsernameNotFoundException("User not found"));
    Address address = new Address(
            customer.getAddress().getId(),
            customer.getAddress().getStreet(),
            customer.getAddress().getHouseNumber(),
            customer.getAddress().getZipCode(),
            customer.getAddress().getCity());


    List<Customer> allNeighbearsList = customerRepository.findByAddressId(customer.getAddress().getId());
        if(allNeighbearsList.isEmpty()) throw new NeighbearsException("The NeighbearsList is empty");
    List<CustomerDTO> dtoNeighbearsList = new ArrayList<>();

    for(Customer one: allNeighbearsList){

        CustomerDTO cDto = new CustomerDTO(
        one.getName(),
                new SelfDescriptionDTO(
                        one.getDescription().getMessage(),
                        one.getDescription().getTitle()
                ),
                new ImageDTO(
                        one.getAvatar().getStorageKey()
                ),
        new AddressDTO(
                one.getAddress().getId(),
                one.getAddress().getStreet(),
                one.getAddress().getHouseNumber(),
                one.getAddress().getZipCode(),
                one.getAddress().getCity()
                ));


                dtoNeighbearsList.add(cDto);


    };

    //allNeighbearsList.stream().map(CustomerDTO::new).collect(Collectors.toList());

        return dtoNeighbearsList;
    }

    @Transactional
    public Long registerUser(RegistrationUserAddressDTO registrationUserAddressDTO) throws NeighbearsException {
        CustomerDTO customerDTO = registrationUserAddressDTO.getCustomerDTO();
        AddressDTO addressDTO = registrationUserAddressDTO.getAddressDTO();

        if(customerRepository.findByEmail(customerDTO.getEmail()).isPresent()){
            throw new NeighbearsException(customerDTO.getEmail() + "is already existing");
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


