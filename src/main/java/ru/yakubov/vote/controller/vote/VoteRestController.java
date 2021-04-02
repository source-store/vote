package ru.yakubov.vote.controller.vote;

/*
 * @autor Alexandr.Yakubov
 **/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yakubov.vote.controller.RestUrlPattern;
import ru.yakubov.vote.model.VoteResult;
import ru.yakubov.vote.service.VoteService;

import java.time.LocalDate;
import java.util.List;

/**
 * GET /rest/votes                                         get result vote current date
 * GET /rest/votes/in?date1=YYYY-MM-DD&date2=YYYY-MM-DD    get result vote by period
 **/

@RestController
@RequestMapping(value = VoteRestController.REST_URL)
public class VoteRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    public static final String REST_URL = RestUrlPattern.ROOT_REST_URL + RestUrlPattern.VOTES_URL;

    @Autowired
    protected VoteService voteService;

    // /rest/votes                                         get result vote current date
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VoteResult> getResultCurdate() {
        log.info("getResultCurdate ");
        return voteService.getResultDate(LocalDate.now(), LocalDate.now());
    }

    // /rest/votes/in?date1=YYYY-MM-DD&date2=YYYY-MM-DD    get result vote by period
    @GetMapping(value = "/in", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VoteResult> getResultDatePeriod(@RequestParam("date1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date1,
                                                @RequestParam("date2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date2) {
        log.info("getResultDatePeriod beginDate {}  endDate {}", date1, date2);
        return voteService.getResultDate(date1, date2);
    }
}
