/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ro.nca.pms.data.entities;

import java.time.ZonedDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * Simple JavaBean domain object representing an person.
 * 
 */
@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
@MappedSuperclass
public class Person extends BaseEntity {

    public Person() {
        super( ZonedDateTime.now() );
    }
    
    @Column(name = "first_name")  
    @NonNull
    protected String firstName;

    @Column(name = "last_name")    
    protected String lastName;
    
    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
    @Column(name = "birth_date")
    private ZonedDateTime birthDate;
    
    /**
     * Personal Identification Code, SSN, CNP
     */
    @Column(name = "pic", unique=true)
    private String pic;


}
