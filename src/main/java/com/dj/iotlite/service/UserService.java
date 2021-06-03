package com.dj.iotlite.service;

import com.dj.iotlite.api.dto.UserDto;
import com.dj.iotlite.api.form.OrganizationForm;
import com.dj.iotlite.api.form.OrganizationQueryForm;
import com.dj.iotlite.api.form.UserForm;
import com.dj.iotlite.api.form.UserQueryForm;
import com.dj.iotlite.entity.organization.Organization;
import com.dj.iotlite.entity.organization.OrganizationRepository;
import com.dj.iotlite.entity.organization.OrganizationUserRepository;
import com.dj.iotlite.entity.repo.UserRepository;
import com.dj.iotlite.entity.user.User;
import com.dj.iotlite.event.ChangeOrganization;
import com.dj.iotlite.event.ChangeUser;
import com.dj.iotlite.utils.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Predicate;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    private ApplicationEventPublisher ep;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    OrganizationUserRepository organizationUserRepository;

    public Page<Organization> getOrganizationList(OrganizationQueryForm query) {
        Specification<Organization> specification = (Specification<Organization>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(query.getWords())) {
                list.add(criteriaBuilder.or(
                        //名称
                        criteriaBuilder.like(root.get("name").as(String.class), "%" + query.getWords() + "%"),
                        //uuid
                        criteriaBuilder.like(root.get("uuid").as(String.class), "%" + query.getWords() + "%"),
                        //备注
                        criteriaBuilder.like(root.get("description").as(String.class), "%" + query.getWords() + "%")
                ));
            }
            if(!ObjectUtils.isEmpty(query.getOrganizationId())){
                list.add(criteriaBuilder.equal(root.get("fid").as(Long.class),query.getOrganizationId()));
            }
            Predicate[] p = new Predicate[list.size()];
            criteriaQuery.where(criteriaBuilder.and(list.toArray(p)));
            return null;
        };
        return organizationRepository.findAll(specification, query.getPage());
    }

    public Object removeOrganization(String uuid) {
        organizationRepository.findFirstByUuid(uuid).ifPresent(organization -> {
            organizationRepository.delete(organization);
        });
        return true;
    }

    public Object saveOrganization(OrganizationForm organizationForm) {
        Organization organization = organizationRepository
                .findFirstByUuid(organizationForm.getUuid()).orElse(new Organization());
        if (ObjectUtils.isEmpty(organizationForm.getUuid())) {
            organization.setUuid(UUID.getUUID());

        } else {

        }
        BeanUtils.copyProperties(organizationForm, organization, "uuid", "id");
        organizationRepository.save(organization);
        ep.publishEvent(new ChangeOrganization(this,organization,ChangeOrganization.Action.ADD));
        return true;
    }

    public Object queryOrganization(String uuid) {
        return null;
    }

    public Object queryUser(String uuid) {
        return null;
    }

    public Page<User> getUserList(UserQueryForm query) {
        Specification<User> specification = (Specification<User>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(query.getWords())) {
                list.add(criteriaBuilder.or(
                        //名称
                        criteriaBuilder.like(root.get("name").as(String.class), "%" + query.getWords() + "%"),
                        //uuid
                        criteriaBuilder.like(root.get("uuid").as(String.class), "%" + query.getWords() + "%"),
                        //备注
                        criteriaBuilder.like(root.get("description").as(String.class), "%" + query.getWords() + "%"),
                        //账户
                        criteriaBuilder.like(root.get("account").as(String.class), "%" + query.getWords() + "%")
                ));
            }
            if(!ObjectUtils.isEmpty(query.getOrganizationId())){
                list.add(criteriaBuilder.equal(root.get("organizationId").as(Long.class),query.getOrganizationId()));
            }
            Predicate[] p = new Predicate[list.size()];
            criteriaQuery.where(criteriaBuilder.and(list.toArray(p)));
            return null;
        };
        return userRepository.findAll(specification, query.getPage());
    }

    public Object removeUser(String uuid) {
        userRepository.findFirstByUuid(uuid).ifPresent(user -> {
            userRepository.delete(user);
        });
        return true;
    }

    public Object saveUser(UserForm userForm) {
        User user = new User();
        BeanUtils.copyProperties(userForm,user);
        user.setIsActive(false);
        userRepository.save(user);
        ep.publishEvent(new ChangeUser(this,user,ChangeUser.Action.ADD));
        return true;
    }

    public Object queryOrganizationTree(Long id) {
        HashMap<String, Object> ret = new HashMap<>();
        Organization parent = new Organization();
        Organization current = new Organization();
        List<Organization> list = new ArrayList<>();
        list = organizationRepository.findAllByFid(id);

        current = organizationRepository.findById(id).orElse(new Organization());

        if(!ObjectUtils.isEmpty(current.getFid())){
            parent = organizationRepository.findById(current.getFid()).orElse(new Organization());
        }
        ret.put("current", current);
        ret.put("parent", parent);
        ret.put("list", list);
        return ret;
    }

    public void refreshChildrenNum(Organization organization) {
        organizationRepository.findById(organization.getFid()).ifPresent(o->{
            o.setChildrenNum(organizationRepository.countAllByFid(organization.getFid()));
            organizationRepository.save(o);
        });
    }

    public void refreshUserNum(User user) {
        organizationRepository.findById(user.getOrganizationId()).ifPresent(organization->{
            organizationRepository.findById(organization.getFid()).ifPresent(o->{
                o.setUserNum(userRepository.countAllByOrganizationId(organization.getFid()));
                organizationRepository.save(o);
            });
        });
    }

    public Object getProfile(User userInfo) {
        UserDto userDto=new UserDto();
        BeanUtils.copyProperties(userInfo,userDto);
        return  userDto;
    }

    public Object saveProfile(User userInfo, UserDto userDto) {
        userInfo.setName(userDto.getName());
        userRepository.save(userInfo);
        return  true;
    }


    @Value("${app.upload}")
    String path;

    @Value("${app.downloadDomain}")
    String downloadDomain;

    public Object updateAvatarImage(MultipartFile file, Long userId) {
        HashMap<String, Object> ret = new HashMap<>();
        try {
            String fileName ="/"+ userId + ".png";
            InputStream is = file.getInputStream();
            String filePath = path + fileName;
            Files.copy(is, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
            ret.put("url", downloadDomain + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        userRepository.findById(userId).ifPresent(u->{
            u.setAvatar((String) ret.get("url"));
            userRepository.save(u);
        });
        return ret;
    }
}
