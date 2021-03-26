package com.example.client.connection.Admin;

import com.example.client.connection.Client.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    ResponseData response = new ResponseData();

    @Bean
    public PasswordEncoder passwordEncrypt() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Autowired
    private PasswordEncoder encrypt;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    //Controller to get the list of all administrator on the platform
    public List<Admin> getAdminList() {
        List<Admin> adminList = adminRepository.findAll();
        for (Admin admin : adminList) {
            admin.setPassword("");
        }
        return adminList;
    }

    //adding new administrator onto the platform
    public ResponseData addNewAdmin(Admin admin) {
        String password = encrypt.encode("password");
        Optional<Admin> adminByMail = this.adminRepository.findAdminByEmail(admin.getEmail());

        if (adminByMail.isPresent()) {
            response.setCode(HttpStatus.BAD_REQUEST.value());
            response.setStatus("Email is already taken");
            HttpStatus.BAD_REQUEST.value();
            throw new IllegalStateException("Email already Taken");
        }
        admin.setPassword(password);
        this.adminRepository.save(admin);
        response.setCode(HttpStatus.OK.value());
        response.setStatus("Admin added Successfully");
        HttpStatus.OK.value();

        return response;
    }

    //Logging of admin
    public ResponseData adminLogin(Admin admin) {
        if (this.adminRepository.findAdminByEmail(admin.getEmail()).isPresent()) {
            Admin admin1 = this.adminRepository.findAdminByEmail(admin.getEmail()).orElse(null);
            if (encrypt.matches(admin.getPassword(), admin1.getPassword())) {
                response.setCode(HttpStatus.OK.value());
                HttpStatus.OK.value();
                response.setStatus("Success");
            } else {
                response.setCode(HttpStatus.BAD_REQUEST.value());
                response.setStatus("Login Failed");
                response.setData(null);
                HttpStatus.BAD_REQUEST.value();
            }
        } else {
            response.setCode(HttpStatus.UNAUTHORIZED.value());
            response.setStatus("Invalid Email or Password");
            HttpStatus.UNAUTHORIZED.value();
        }
        return response;
    }

    public ResponseData changePassword(Admin admin){
        Optional<Admin> foundAdmin = this.adminRepository.findAdminByEmail(admin.getEmail());
        if (foundAdmin.isPresent()){
          foundAdmin.get().setPassword(admin.getPassword());
            response.setCode(HttpStatus.OK.value());
            response.setStatus("success");
            HttpStatus.OK.value();
            this.adminRepository.save(admin);
        }else{

        }
        return response;

    }

    //Deleting an admin
    public void removeAdmin(Long id){
        this.adminRepository.deleteById(id);
    }
}
