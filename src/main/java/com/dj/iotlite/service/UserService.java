package com.dj.iotlite.service;

import com.dj.iotlite.api.form.OrganizationQueryForm;
import com.dj.iotlite.api.form.UserQueryForm;
import com.dj.iotlite.entity.organization.Organization;
import com.dj.iotlite.entity.organization.OrganizationRepository;
import com.dj.iotlite.entity.organization.OrganizationUserRepository;
import com.dj.iotlite.entity.user.User;
import com.dj.iotlite.entity.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserService {

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
                        criteriaBuilder.like(root.get("remark").as(String.class), "%" + query.getWords() + "%")
                ));
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

    public Object saveOrganization() {
        return null;
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
                        criteriaBuilder.like(root.get("remark").as(String.class), "%" + query.getWords() + "%"),
                        //账户
                        criteriaBuilder.like(root.get("account").as(String.class), "%" + query.getWords() + "%")
                ));
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

    public Object saveUser() {
        return null;
    }
}
