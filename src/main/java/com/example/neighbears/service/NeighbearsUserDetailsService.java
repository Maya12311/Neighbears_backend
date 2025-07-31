package com.example.neighbears.service;

import com.example.neighbears.dto.CustomerDTO;
import com.example.neighbears.exceptions.EmailAlreadyUsedException;
import com.example.neighbears.model.Customer;
import com.example.neighbears.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
public class NeighbearsUserDetailsService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    private final Environment environment;

    public NeighbearsUserDetailsService(PasswordEncoder passwordEncoder, CustomerRepository customerRepository, Environment environment) {
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
        this.environment = environment;
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("try try tray"+ username.toString());

        Optional<Customer> optional= customerRepository.findByEmail(username);
        Customer customer = optional.orElseThrow(() ->
                new UsernameNotFoundException("The user wasn't found: " + username));
        //Customer customer =customerRepository.findByEmail(username).orElseThrow(() ->
         //      new UsernameNotFoundException("User details not found for the user" + username));
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(customer.getRole()));
        return new User(customer.getEmail(), customer.getPwd(), authorities);
    }

    public UserDetails loadUserByAuthority(Authentication auth) throws UsernameNotFoundException {
System.out.println("try maus tray"+ auth);
        Optional<Customer> optional= customerRepository.findByEmail(auth.getName());
        Customer cus = optional.orElseThrow(() ->
                new UsernameNotFoundException("The user wasn't found: " + auth.getName()));
        //Customer customer =customerRepository.findByEmail(username).orElseThrow(() ->
        //      new UsernameNotFoundException("User details not found for the user" + username));
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(cus.getRole()));
        return new User(cus.getEmail(), cus.getPwd(), authorities);
    }

    public Long registerUser(CustomerDTO customerDTO) {
        Optional<Customer> existingCustomer = customerRepository.findByEmail(customerDTO.getEmail());

        if(existingCustomer.isPresent()){
    throw new EmailAlreadyUsedException("Diese Email ist bereits vergeben");
        }else {

            Customer customer = new Customer();
            customer.setEmail(customerDTO.getEmail());
            customer.setRole(customerDTO.getRole());

            String hashPwd = passwordEncoder.encode(customerDTO.getPwd());
            customer.setPwd(hashPwd);
            Customer savedCustomer = customerRepository.save(customer);
            return savedCustomer.getId();
        }
    }

}