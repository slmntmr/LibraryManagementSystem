package com.tpe.controller;

import com.tpe.domain.Owner;
import com.tpe.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/owners") // http://localhost:8080/owners
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    // Save an Owner
    @PostMapping("/save") // http://localhost:8080/owners/save + JSON + POST
    public ResponseEntity<Map<String, String>> saveOwner(@Valid @RequestBody Owner owner){

        ownerService.saveOwner(owner);

        Map<String, String> msg = new HashMap<>();
        msg.put("message", "Owner has been saved successfully.");

        return new ResponseEntity<>(msg, HttpStatus.CREATED); // 201

    }

    // Find All Owners
    @GetMapping // http://localhost:8080/owners
    public ResponseEntity<List<Owner>> getAllOwners(){

        List<Owner> owners = ownerService.getAllOwners();

        return ResponseEntity.ok(owners); // 200

    }

    // Find an Owner By ID
    @GetMapping("/{id}") // http://localhost:8080/owners/2
    public ResponseEntity<Owner> findOwnerByID(@PathVariable Long id){

        Owner owner = ownerService.findOwnerByID(id);
        return ResponseEntity.ok(owner);

    }

    // Add a Book to an Owner
    // http://localhost:8080/owners/1?add=2
    @PostMapping("/{id}")
    public ResponseEntity<Map<String,String>> addBookToOwner(@PathVariable("id") Long ownerID, @RequestParam("add") Long bookID){

        ownerService.addBookToOwner(ownerID, bookID);

        Map<String, String> map = new HashMap<>();
        map.put("message", "Book has been registered successfully.");

        return ResponseEntity.ok(map); // 200

    }

}
