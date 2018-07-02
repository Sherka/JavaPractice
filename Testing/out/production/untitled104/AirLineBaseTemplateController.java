package ru.dz.bis.controllers;

import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;
import ru.dz.bis.dto.AirLineBaseTemplateBaseDto;
import ru.dz.bis.dto.AirLineBaseTemplateDto;
import ru.dz.bis.elastic.service.UniversalElasticService;
import ru.dz.bis.service.AirLineBaseTemplateRestService;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
public class AirLineBaseTemplateController {

    private final AirLineBaseTemplateRestService restService;
    private final UniversalElasticService elasticService;

    @Autowired
    public AirLineBaseTemplateController(AirLineBaseTemplateRestService restService, UniversalElasticService elasticService) {
        this.restService = restService;
        this.elasticService = elasticService;
    }

    @RequestMapping(value = "airlinebasetemplate/{id}", method = RequestMethod.GET)
    public AirLineBaseTemplateDto getAirLineBaseTemplate(@PathVariable Long id) {
        return restService.get(id);
    }

    @RequestMapping(value = "airlinebasetemplate", method = RequestMethod.POST)
    public AirLineBaseTemplateDto saveAirLineBaseTemplate (@RequestBody AirLineBaseTemplateBaseDto dto) {
        return restService.save(dto);
    }

    @RequestMapping(value = "airlinebasetemplate/{id}", method = RequestMethod.DELETE)
    public void deleteAirLineBaseTemplate (@PathVariable Long id) {
        restService.delete(id);
    }

    @RequestMapping(value = "airlinebasetemplate/{id}", method = RequestMethod.PUT)
    public AirLineBaseTemplateDto updateAirLineBaseTemplate (@PathVariable Long id, @RequestBody AirLineBaseTemplateBaseDto dto) {
        return restService.update(id, dto);
    }

    @RequestMapping(value = "airlinebasetemplate/elastic/list/filter", method = RequestMethod.GET)
    public List<AirLineBaseTemplateDto> findAllByElasticJsonFilter(@RequestParam(name = "filters", defaultValue = "{}") String filterParams,
                                                                   @RequestParam(defaultValue = "0") Integer page,
                                                                   @RequestParam(defaultValue = "30") Integer size,
                                                                   @RequestParam(required = false) String sortdirection,
                                                                   @RequestParam(required = false) String sortcolumn) throws IOException {

        final Pageable pageRequest = new PageRequest(page, size);

        if( sortcolumn == null ) {
            return elasticService.findAllJsonFilter(pageRequest, null, null, filterParams, AirLineBaseTemplateDto.class);
        } else {
            return elasticService.findAllJsonFilter(pageRequest, Arrays.asList(sortcolumn),
                    sortdirection != null ? Arrays.asList(SortOrder.fromString(sortdirection)) : null, filterParams,
                    AirLineBaseTemplateDto.class);
        }

    }

}
