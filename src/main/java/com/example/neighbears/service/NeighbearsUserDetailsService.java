package com.example.neighbears.service;

import com.example.neighbears.dto.*;
import com.example.neighbears.exceptions.NeighbearsException;
import com.example.neighbears.model.Address;
import com.example.neighbears.model.Customer;
import com.example.neighbears.model.SelfDescription;
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

import java.io.IOException;
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

    public NeighbearsUserDetailsService(PasswordEncoder passwordEncoder, AddressRepository addressRepository,
                                        CustomerRepository customerRepository, Environment environment) {
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

    public List<CustomerDTO> getAllNeighbears( String email)  {
       List<Customer> neighbearsList = new ArrayList<>();

 Optional<Customer> optional = customerRepository.findByEmail(email);
    Customer customer = optional.orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String message = null;
        String title = null;
        String storageKey = null;
        String contentType = null;
        byte[] avatar = new byte[0];

        Long Id = customer.getAddress().getId() != null ? customer.getAddress().getId() : 0;

        List<Customer> allNeighbearsList = customerRepository.findByAddressIdWithDetails(Id);
        if(allNeighbearsList.isEmpty()) throw new NeighbearsException("The NeighbearsList is empty");
    List<CustomerDTO> dtoNeighbearsList = new ArrayList<>();

    for(Customer one: allNeighbearsList){
        SelfDescription desc = one.getDescription();
        String descriptionMessage = (desc != null && desc.getMessage() != null)
                ? desc.getMessage()
                : "<no-desc>";

        String descriptionTitle = (desc != null && desc.getTitle() != null)
                ? desc.getTitle()
                : "<no-title>";


        if (one.getAvatar() != null) {
            storageKey = one.getAvatar().getStorageKey();
            contentType = one.getAvatar().getContentType();
            
        }else{
            storageKey = "";
            contentType = "";
        }

        Address address = one.getAddress();

        Long addressId = (address != null && address.getId() != null)
                ? address.getId()
                : 0L;

        String addressStreet = (address != null && address.getStreet() != null)
                ? address.getStreet()
                : "<no-street>";

        String addressHouseNumber = (address != null && address.getHouseNumber() != null)
                ? address.getHouseNumber()
                : "";

        String addressZipCode = (address != null && address.getZipCode() != null)
                ? address.getZipCode()
                : "";

        String addressCity = (address != null && address.getCity() != null)
                ? address.getCity()
                : "";



        CustomerDTO cDto = new CustomerDTO(
                one.getId(),
        one.getName(),
                new SelfDescriptionDTO(
                       descriptionMessage,
                        descriptionTitle
                ),
                new ImageDTO(
                contentType,
                        storageKey,
                        avatar

                ),
        new AddressDTO(
                addressId,
               addressStreet,
               addressHouseNumber,
                addressZipCode,
                addressCity
                ));


                dtoNeighbearsList.add(cDto);


    };

    //allNeighbearsList.stream().map(CustomerDTO::new).collect(Collectors.toList());
for(CustomerDTO blu: dtoNeighbearsList){
    System.out.println("last test in the backend "+ blu.getAddressDTO().getStreet()+ blu.getName()+ blu.getAddressDTO().getCity());

}
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


