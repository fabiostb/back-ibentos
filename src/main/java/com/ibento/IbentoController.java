package com.ibento;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/ibento")
public class IbentoController {

    @Autowired
    private IbentoService service;

    @RequestMapping(method = GET, value = "{id}")
    public IbentoDto get(@PathVariable String id) {
        return this.service.getIbento(id);
    }

    @RequestMapping(method = GET)
    public List<IbentoListDto> get() {
        return this.service.getIbentos();
    }

    @RequestMapping(method = GET, value = "/find")
    public List<IbentoDto> search(@RequestParam String name) {
        return this.service.getIbentos(name);
    }

    @RequestMapping(method = POST)
    public IbentoDto create(@RequestBody IbentoDto ibentoDto) {
        return this.service.createIbento(ibentoDto);
    }

    @RequestMapping(method = PUT)
    public IbentoDto update(@RequestBody IbentoDto ibentoDto) {
        return this.service.updateIbento(ibentoDto);
    }

    @RequestMapping(method = DELETE, value = "{id}")
    public void delete(@PathVariable String id) {
        this.service.deleteIbento(id);
    }

}
