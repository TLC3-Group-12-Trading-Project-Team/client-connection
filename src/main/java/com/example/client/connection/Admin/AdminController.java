package com.example.client.connection.Admin;

import com.example.client.connection.Client.ClientService;
import com.example.client.connection.Client.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public List<Admin> getAdmin(){
        return this.adminService.getAdminList();
    }

    @PostMapping(path = "/signup")
    public ResponseData registerNewAdmin(@RequestBody Admin admin){
        return this.adminService.addNewAdmin(admin);
    }

    @PostMapping(path = "/login")
    public ResponseData loginAdmin(@RequestBody Admin admin){
        return this.adminService.adminLogin(admin);
    }
}
