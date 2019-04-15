package com.vcv.frontend.screens;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vcv.backend.entities.User;
import com.vcv.backend.views.UserView;
import com.vcv.frontend.grids.EmployeeGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

public class AdminScreen extends VerticalLayout {
    @Autowired private RestTemplate restTemplate;

    private User admin;
    private EmployeeGrid grid;

    private String baseURL = "http://vcv/admin/";

    public AdminScreen() throws URISyntaxException {
        URI uri = new URI(baseURL + "/getEmployees");
        ResponseEntity<UserView[]> response = restTemplate.postForEntity(uri, admin, UserView[].class);
        ListDataProvider<UserView> dataProvider = new ListDataProvider<>(Arrays.asList(response.getBody()));

        grid = new EmployeeGrid();
        grid.setDataProvider(dataProvider);
    }
}
