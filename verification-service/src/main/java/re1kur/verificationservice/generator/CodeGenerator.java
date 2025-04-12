package re1kur.verificationservice.generator;

import re1kur.verificationservice.entity.Code;

@FunctionalInterface
public interface CodeGenerator {
    Code generate(String email);
}
