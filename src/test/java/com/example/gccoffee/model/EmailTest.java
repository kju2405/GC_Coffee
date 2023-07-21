package com.example.gccoffee.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    @DisplayName("잘못된 email")
    void testInvalidEmail() {
        //given
        String email = "kju2405@gm.ail.comdfdfdsd";

        //when
        assertThatThrownBy(() -> new Email(email)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("제대로된 email")
    void testValidEmail(){
        //given
        String email = "kju2405@gmail.com";

        //then
        Email newEmail = new Email(email);

        //then
        assertThat(newEmail).isNotNull();
        assertThat(newEmail.getAddress()).isEqualTo(email);
    }

    @Test
    @DisplayName("equals and hashcode 오버라이드 검사")
    void testEqualEmail(){
        //given
        String email1 = "hello@gmail.com";
        String email2 = "hello@gmail.com";
        Email newEmail1 = new Email(email1);
        Email newEmail2 = new Email(email2);

        //when
        assertThat(newEmail1).isEqualTo(newEmail2);
    }
}