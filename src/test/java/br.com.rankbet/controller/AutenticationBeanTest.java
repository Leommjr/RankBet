package br.com.rankbet.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

public class AutenticationBeanTest {

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAutenticationOK() throws IOException {
        System.out.println("HELLO");
    }
}