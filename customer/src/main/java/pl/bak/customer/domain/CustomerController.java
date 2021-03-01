package pl.bak.customer.domain;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.bak.customer.dto.CustomerDto;
import pl.bak.customer.model.Customer;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(value = "/create",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer(@RequestBody @Valid CustomerDto customerDto){
       return customerService.saveCustomer(customerDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT));
    }

    @GetMapping("/all")
    public List<CustomerDto> getCustomers(@RequestParam(name = "creditId") List<Integer> creditId){
        List<CustomerDto> all = customerService.getAll(creditId);

        if (all.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        return all;
    }
}
