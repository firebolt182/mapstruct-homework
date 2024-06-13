package org.javaacademy.mapstruct_homework.mapper;

import org.javaacademy.mapstruct_homework.dto.PersonCreditDto;
import org.javaacademy.mapstruct_homework.dto.PersonDriverLicenceDto;
import org.javaacademy.mapstruct_homework.dto.PersonInsuranceDto;
import org.javaacademy.mapstruct_homework.entity.Human;
import org.javaacademy.mapstruct_homework.entity.Passport;
import org.javaacademy.mapstruct_homework.entity.Work;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;

import java.time.LocalDate;

@Mapper
public interface HumanMapper {

    @Mapping(target = "passportNumber", source = ".", qualifiedByName = "getPassportNumber")
    @Mapping(target = "salary", source = ".", qualifiedByName = "getSalary")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fullAddress", source = ".", qualifiedByName = "getAddress")
    PersonCreditDto convertToCreditDto(Human human);

    @Mapping(target = "fullName", source = ".", qualifiedByName = "getFullName")
    @Mapping(target = "fullPassportData", source = ".", qualifiedByName = "getFullPassportData")
    @Mapping(target = "birthDate", source = ".", qualifiedByName = "getBirthDate")
    PersonDriverLicenceDto convertToDriverLicenceDto(Human human);

    @Mapping(target = "fullName", source = ".", qualifiedByName = "getFullName")
    @Mapping(target = "fullAddress", source = ".", qualifiedByName = "getAddress")
    @Mapping(target = "fullAge", source = ".", qualifiedByName = "getAge")
    PersonInsuranceDto convertToInsuranceDto(Human human);

    @Named("getPassportNumber")
    default String getPassportNumber(Human human) {
        return "%s %s".formatted(human.getPassport().getSeries(), human.getPassport().getNumber());
    }

    @Named("getSalary")
    default String getSalary(Human human) {
        return "%s %s".formatted(human.getWork().getSalary(), human.getWork().getCurrency());
    }

    @Named("getAddress")
    default String getAddress(Human human) {
        if (human.getLivingAddress().getRegion() == null && human.getLivingAddress().getFlat() == null) {
            return "%s %s %s".formatted(
                    human.getLivingAddress().getCity(),
                    human.getLivingAddress().getStreet(),
                    human.getLivingAddress().getHouse());
        }
        return "%s %s %s %s %s".formatted(
                human.getLivingAddress().getRegion(),
                human.getLivingAddress().getCity(),
                human.getLivingAddress().getStreet(),
                human.getLivingAddress().getHouse(),
                human.getLivingAddress().getFlat());
    }

    @Named("getFullName")
    default String getFullName(Human human) {
        if (human.getMiddleName().isEmpty()) {
            return "%s %s".formatted(human.getFirstName(), human.getLastName());
        }
        return "%s %s %s".formatted(
                human.getFirstName(),
                human.getLastName(),
                human.getMiddleName());
    }

    @Named("getFullPassportData")
    default String getFullPassportData(Human human) {
        String issueDate = String.format("%s.%s.%s",
                human.getPassport().getIssueDate().getDayOfMonth(),
                human.getPassport().getIssueDate().getMonth().getValue(),
                human.getPassport().getIssueDate().getYear());
        return "%s%s %s".formatted(
                human.getPassport().getSeries(),
                human.getPassport().getNumber(),
                issueDate
                );
    }

    @Named("getBirthDate")
    default String getBirthDate(Human human) {
        return "%s.%s.%s".formatted(
                human.getBirthDay(),
                human.getBirthMonth(),
                human.getBirthYear());
    }

    @Named("getAge")
    default Integer getAge(Human human) {
        Integer age = LocalDate.now().getYear() - human.getBirthYear();
        return age;
    }

}
