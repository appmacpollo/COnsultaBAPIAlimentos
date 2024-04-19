package com.macpollo.app.consultabapialimentoswebservice.controllers;

import com.macpollo.app.consultabapialimentoswebservice.services.SapBapiConsumer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.json.JSONObject;

@WebServlet("/materialGetList")
public class MaterialGetListServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Llamar al método correspondiente
        JSONObject result = SapBapiConsumer.retrieveMaterialGetList(
                Integer.parseInt(request.getParameter("maxRows")),
                request.getParameter("matnrSelectionSign"),
                request.getParameter("matnrSelectionOption"),
                request.getParameter("matnrSelectionDescrLow"),
                request.getParameter("matnrSelectionDescrHigh"),
                request.getParameter("materialShortDescSelSign"),
                request.getParameter("materialShortDescSelOption"),
                request.getParameter("materialShortDescSelDescrLow"),
                request.getParameter("materialShortDescSelDescrHigh"),
                request.getParameter("manufacturerPartNumManuMat"),
                request.getParameter("manufacturerPartNumMfrNo"),
                request.getParameter("plantSelectionSign"),
                request.getParameter("plantSelectionOption"),
                request.getParameter("plantSelectionDescrLow"),
                request.getParameter("plantSelectionDescrHigh"),
                request.getParameter("storageLocationSelectSign"),
                request.getParameter("storageLocationSelectOption"),
                request.getParameter("storageLocationSelectDescrLow"),
                request.getParameter("storageLocationSelectDescrHigh"),
                request.getParameter("salesOrganisationSelectionSign"),
                request.getParameter("salesOrganisationSelectionOption"),
                request.getParameter("salesOrganisationSelectionDescrLow"),
                request.getParameter("salesOrganisationSelectionDescrHigh"),
                request.getParameter("distributionChannelSelectionSign"),
                request.getParameter("distributionChannelSelectionOption"),
                request.getParameter("distributionChannelSelectionDescrLow"),
                request.getParameter("distributionChannelSelectionDescrHigh")
        );

        // Enviar respuesta como JSON
        response.setContentType("application/json");
        response.getWriter().print(result.toString());
    }
}
