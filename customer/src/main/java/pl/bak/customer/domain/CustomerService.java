package pl.bak.customer.domain;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.bak.customer.domain.dao.CreditRepository;
import pl.bak.customer.domain.dao.CustomerRepository;
import pl.bak.customer.dto.CustomerDto;
import pl.bak.customer.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CreditRepository creditRepository;
    private final ModelMapper modelMapper;

    public CustomerService(CustomerRepository customerRepository, CreditRepository creditRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.creditRepository = creditRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public Optional<Customer> saveCustomer(CustomerDto customerDto) {
        int id = customerDto.getCreditDto().getId();

        if (customerRepository.findByPesel(customerDto.getPesel()).isPresent()) {
            creditRepository.deleteById(id);
            return Optional.empty();
        }

        Customer customer = modelMapper.map(customerDto, Customer.class);
        customer.setId(null);

        creditRepository.findById(id)
                .ifPresent(customer::setCredit);

        customerRepository.save(customer);

        return Optional.of(customer);
    }

    public List<CustomerDto> getAll(List<Integer> creditId) {
        List<Customer> customers = customerRepository.findAllById(creditId);

        return customers.stream()
                .map(customer -> modelMapper.map(customer, CustomerDto.class))
                .collect(Collectors.toList());
    }


}
