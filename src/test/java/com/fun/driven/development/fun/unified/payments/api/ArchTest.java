package com.fun.driven.development.fun.unified.payments.api;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.fun.driven.development.fun.unified.payments.api");

        noClasses()
            .that()
                .resideInAnyPackage("com.fun.driven.development.fun.unified.payments.api.service..")
            .or()
                .resideInAnyPackage("com.fun.driven.development.fun.unified.payments.api.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.fun.driven.development.fun.unified.payments.api.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
