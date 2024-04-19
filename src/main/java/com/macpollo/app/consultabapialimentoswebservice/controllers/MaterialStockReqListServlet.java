package com.macpollo.app.consultabapialimentoswebservice.controllers;

import com.macpollo.app.consultabapialimentoswebservice.services.SapBapiConsumer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.json.JSONObject;

@WebServlet("/materialStockReqList")
public class MaterialStockReqListServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recuperar parámetros opcionales
        Boolean indicadorPeriodo = Boolean.parseBoolean(request.getParameter("indicadorPeriodo"));
        Boolean obtenerDetalle = Boolean.parseBoolean(request.getParameter("obtenerDetalle"));
        Boolean obtenerLineas = Boolean.parseBoolean(request.getParameter("obtenerLineas"));
        Boolean obtenerLineaTotales = Boolean.parseBoolean(request.getParameter("obtenerLineaTotales"));
        Boolean ignorarBuffer = Boolean.parseBoolean(request.getParameter("ignorarBuffer"));

        // Llamar al método correspondiente
        JSONObject result = SapBapiConsumer.retrieveMaterialStockReqList(
                request.getParameter("codigoMaterial"),
                request.getParameter("centro"),
                request.getParameter("mrpArea"),
                request.getParameter("reglaSeleccion"),
                request.getParameter("reglaEjecucion"),
                request.getParameter("materialExterno"),
                request.getParameter("versionMaterial"),
                request.getParameter("materialIdExterno"),
                indicadorPeriodo,
                obtenerDetalle,
                obtenerLineas,
                obtenerLineaTotales,
                ignorarBuffer
        );

        // Enviar respuesta como JSON
        response.setContentType("application/json");
        response.getWriter().print(result.toString());
    }
}
