package com.example.neighbears.service;

import com.example.neighbears.dto.CustomerDTO;
import com.example.neighbears.dto.SelfDescriptionDTO;
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

    public SelfDescriptionDTO saveDescription(SelfDescriptionDTO selfDescriptionDTO, Authentication authentication) throws UserIdNotFoundException {
        System.out.println("im in the testiiii");
        System.out.println("Miau.  "+selfDescriptionDTO.getMessage());

        //System.out.println("is the authentication there? "+authentication);
       // CustomerDTO customerDTO = userDetailsService.getUserByEmail(authentication.getName());
       // selfDescriptionDTO.setCustomer(customerDTO);
        Optional<Customer> optional = customerRepository.findByEmail(authentication.getName());
        Customer customer = optional.orElseThrow(() -> new UserIdNotFoundException("not found"));

        System.out.println("BLU" + customer.getId());


        // System.out.println("now the customerDTO? "+customerDTO.getId()+ "actually now"+ customerDTO);
SelfDescription selfDescription = new SelfDescription(
        selfDescriptionDTO.getCustomerId(), selfDescriptionDTO.getTitle(),
        selfDescriptionDTO.getMessage(), selfDescriptionDTO.getCreatedAt(),
        selfDescriptionDTO.getUpdatedAt()
);

selfDescription.setCustomer(customer);
        System.out.println("BLU again" + selfDescription);


if (selfDescription == null) {
    throw new IllegalArgumentException("The description is empty");
}
        System.out.println("BLU again" + selfDescription.getCustomer().getName());
        System.out.println("BLU again" + selfDescription.getCustomer().getId());
        System.out.println("BLU again" + selfDescription.getCustomer().getEmail());


        SelfDescription selfDesc = profileRepository.save(selfDescription);


        SelfDescriptionDTO selfdto = new SelfDescriptionDTO(
                selfDesc.getCustomerId(),
                selfDesc.getTitle(),
                selfDesc.getMessage(),
                selfDesc.getCreatedAt(),
                selfDesc.getUpdatedAt()
        );

System.out.println("what ist it bluuuu.  "+selfdto);
        return selfdto;
    }
}
