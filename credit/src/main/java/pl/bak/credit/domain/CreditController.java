package pl.bak.credit.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.bak.credit.dto.MainDto;
import pl.bak.credit.model.Credit;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/credit")
public class CreditController {
    private final CreditService creditService;

    public CreditController(CreditService creditService) {
        this.creditService = creditService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Credit createCredit(@RequestBody @Valid MainDto mainDto) {
        return creditService.createCredit(mainDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/all")
    public List<MainDto> getCredits() {
        List<MainDto> all = creditService.getAll();

        if (all.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        return all;
    }
}
