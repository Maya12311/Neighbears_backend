package com.example.neighbears.service;

import com.example.neighbears.dto.CustomerDTO;
import com.example.neighbears.dto.SelfDescriptionDTO;
import com.example.neighbears.exceptions.SelfDescriptionNotFoundException;
import com.example.neighbears.exceptions.UserIdNotFoundException;
import com.example.neighbears.model.Customer;
import com.example.neighbears.model.SelfDescription;
import com.example.neighbears.repository.CustomerRepository;
import com.example.neighbears.repository.ProfileRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserProfileService {

    private final ProfileRepository profileRepository;
    private final CustomerRepository customerRepository;
    public UserProfileService(ProfileRepository profileRepository, CustomerRepository customerRepository) {
        this.profileRepository = profileRepository;
        this.customerRepository = customerRepository;
    }

    public SelfDescriptionDTO getDescription(String email) throws SelfDescriptionNotFoundException{
        Optional<Customer> opt= customerRepository.findByEmail(email);
        Customer customer = opt.orElseThrow(() -> new UserIdNotFoundException("User not found"));


        Optional<SelfDescription> optional = profileRepository.findByCustomerId(customer.getId());
        SelfDescription selfDescription = optional.orElseThrow(() -> new SelfDescriptionNotFoundException("Not Found"));

        SelfDescriptionDTO selfDescriptionDTO =
                new SelfDescriptionDTO(selfDescription.getCustomerId(),
                        selfDescription.getTitle(),
                        selfDescription.getMessage(),
                        selfDescription.getCreatedAt(),
                        selfDescription.getUpdatedAt());
        return selfDescriptionDTO;
    }

    public SelfDescriptionDTO saveDescription(SelfDescriptionDTO selfDescriptionDTO, Authentication authentication) throws UserIdNotFoundException {


        //System.out.println("is the authentication there? "+authentication);
       // CustomerDTO customerDTO = userDetailsService.getUserByEmail(authentication.getName());
       // selfDescriptionDTO.setCustomer(customerDTO);
        Optional<Customer> optional = customerRepository.findByEmail(authentication.getName());
        Customer customer = optional.orElseThrow(() -> new UserIdNotFoundException("User not found"));


        // System.out.println("now the customerDTO? "+customerDTO.getId()+ "actually now"+ customerDTO);
SelfDescription selfDescriptionEntity = profileRepository.findByCustomerId(customer.getId())
                .orElseGet(() -> {
                    SelfDescription newSelfDescription = new SelfDescription();
                    newSelfDescription.setCustomer(customer);
                    return newSelfDescription;
                });

selfDescriptionEntity.setTitle(selfDescriptionDTO.getTitle());
selfDescriptionEntity.setMessage(selfDescriptionDTO.getMessage());




if (selfDescriptionEntity == null) {
    throw new IllegalArgumentException("The description is empty");
}
SelfDescription selfDesc = profileRepository.save(selfDescriptionEntity);


        SelfDescriptionDTO selfdto = new SelfDescriptionDTO(
                selfDesc.getCustomerId(),
                selfDesc.getTitle(),
                selfDesc.getMessage(),
                selfDesc.getCreatedAt(),
                selfDesc.getUpdatedAt()
        );

        return selfdto;
    }
}
