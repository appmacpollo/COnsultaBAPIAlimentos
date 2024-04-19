/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.macpollo.app.consultabapialimentoswebservice.services;

/**
 * BAPI_MATERIAL_STOCK_REQ_LIST: Descripción: Esta BAPI se utiliza para
 * recuperar información sobre los elementos de planificación de necesidades
 * (MRP) para un material específico en una planta dada. Proporciona detalles
 * sobre las órdenes de compra abiertas, entregas abiertas y requisiciones de
 * compra abiertas.
 *
 * Parámetros de entrada: MATERIAL: Número de material. PLANT: Código de planta.
 * MRP_AREA: Área de planificación. PLAN_SCENARIO: Escenario de planificación.
 * SELECTION_RULE: Regla de selección. DISPLAY_FILTER: Filtro de visualización.
 * PERIOD_INDICATOR: Indicador de período. GET_ITEM_DETAILS: Detalles del
 * elemento. GET_IND_LINES: Líneas de indicador MRP. GET_TOTAL_LINES: Líneas
 * totales de MRP. IGNORE_BUFFER: Ignorar el búfer. MATERIAL_EVG: Material EVG.
 *
 * Tablas de salida: MRP_ITEMS: Elementos MRP. MRP_IND_LINES: Líneas de
 * indicador MRP. MRP_TOTAL_LINES: Líneas totales de MRP. EXTENSIONOUT: Datos de
 * extensión.
 *
 * --------------------------------------------------------------------------------------------------------------------------------------------------------------
 *
 * BAPI_MATERIAL_GETLIST: Descripción: Esta BAPI se utiliza para obtener una
 * lista de materiales que cumplen con ciertos criterios de búsqueda.
 *
 * Parámetros de entrada: MAXROWS: Número máximo de filas a devolver.
 *
 * Tablas de salida: MATNRSELECTION: Selección de números de material.
 * MATERIALSHORTDESCSEL: Selección de descripciones cortas de material.
 * MANUFACTURERPARTNUMB: Número de pieza del fabricante. PLANTSELECTION:
 * Selección de códigos de planta. STORAGELOCATIONSELECT: Selección de
 * ubicaciones de almacenamiento. SALESORGANISATIONSELECTION: Selección de
 * organizaciones de ventas. DISTRIBUTIONCHANNELSELECTION: Selección de canales
 * de distribución. MATNRLIST: Lista de números de material. RETURN: Mensajes de
 * retorno.
 */
/**
 *
 * @author Oficina
 */
import com.sap.conn.jco.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class SapBapiConsumer {

    public static JSONObject retrieveMaterialStockReqList(
            String codigoMaterial,
            String centro,
            String mrpArea,
            String reglaSeleccion,
            String reglaEjecucion,
            String materialExterno,
            String versionMaterial,
            String materialIdExterno,
            Boolean indicadorPeriodo,
            Boolean obtenerDetalle,
            Boolean obtenerLineas,
            Boolean obtenerLineaTotales,
            Boolean ignorarBuffer
    ) {
        JSONObject response = new JSONObject();
        try {
            JCoDestination destination = JCoDestinationManager.getDestination("config/conexion");
            JCoFunction function = destination.getRepository().getFunction("BAPI_MATERIAL_STOCK_REQ_LIST");

            // Set input parameters
            response.put("PARAMETERS", setImportParameters(function, fillCode(codigoMaterial, 18), centro, mrpArea, reglaSeleccion, reglaEjecucion,
                    materialExterno, versionMaterial, materialIdExterno, indicadorPeriodo, obtenerDetalle,
                    obtenerLineas, obtenerLineaTotales, ignorarBuffer));

            function.execute(destination);

            // Retrieve output tables and export parameters
            response.put("RESPONSE", retrieveOutputData(function));

        } catch (JCoException e) {
            JSONObject error = new JSONObject();
            error.put("ERROR_MESSAGE", e.getMessage());
//            error.put("ERROR_STACK_TRACE", e.printStackTrace());
            response.put("ERROR", error);
        }
        return response;
    }

    private static JSONObject setImportParameters(
            JCoFunction function,
            String codigoMaterial,
            String centro,
            String mrpArea,
            String reglaSeleccion,
            String reglaEjecucion,
            String materialExterno,
            String versionMaterial,
            String materialIdExterno,
            Boolean indicadorPeriodo,
            Boolean obtenerDetalle,
            Boolean obtenerLineas,
            Boolean obtenerLineaTotales,
            Boolean ignorarBuffer
    ) {
        JCoParameterList importParams = function.getImportParameterList();
        importParams.setValue("MATERIAL", codigoMaterial);
        importParams.setValue("PLANT", centro);
        importParams.setValue("MRP_AREA", mrpArea);
        importParams.setValue("PLAN_SCENARIO", 000);
        importParams.setValue("SELECTION_RULE", reglaSeleccion);
        importParams.setValue("DISPLAY_FILTER", "");
        importParams.setValue("PERIOD_INDICATOR", indicadorPeriodo ? "X" : "");
        importParams.setValue("GET_ITEM_DETAILS", obtenerDetalle ? "X" : "");
        importParams.setValue("GET_IND_LINES", obtenerLineas ? "X" : "");
        importParams.setValue("GET_TOTAL_LINES", obtenerLineaTotales ? "X" : "");
        importParams.setValue("IGNORE_BUFFER", ignorarBuffer ? "X" : "");

        JCoStructure mrpListaimpo = importParams.getStructure("MATERIAL_EVG");
        mrpListaimpo.setValue("MATERIAL_EXT", materialExterno);
        mrpListaimpo.setValue("MATERIAL_VERS", versionMaterial);
        mrpListaimpo.setValue("MATERIAL_GUID", materialIdExterno);

        // Crear el objeto JSON
        JSONObject json = new JSONObject();

        // Llenar el objeto con los datos proporcionados
        json.put("MATERIAL", fillCode(codigoMaterial, 18));
        json.put("PLANT", centro);
        json.put("MRP_AREA", mrpArea);
        json.put("PLAN_SCENARIO", 000);
        json.put("SELECTION_RULE", reglaSeleccion);
        json.put("DISPLAY_FILTER", "");
        json.put("PERIOD_INDICATOR", indicadorPeriodo ? "X" : "");
        json.put("GET_ITEM_DETAILS", obtenerDetalle ? "X" : "");
        json.put("GET_IND_LINES", obtenerLineas ? "X" : "");
        json.put("GET_TOTAL_LINES", obtenerLineaTotales ? "X" : "");
        json.put("IGNORE_BUFFER", ignorarBuffer ? "X" : "");

        // Crear el objeto JSON para MRP_LISTA_IMPO
        JSONObject jsonMrpListaimpo = new JSONObject();
        jsonMrpListaimpo.put("MATERIAL_EXT", materialExterno);
        jsonMrpListaimpo.put("MATERIAL_VERS", versionMaterial);
        jsonMrpListaimpo.put("MATERIAL_GUID", materialIdExterno);

        // Agregar el objeto MRP_LISTA_IMPO al objeto principal
        json.put("MATERIAL_EVG", jsonMrpListaimpo);

        return json;

    }

    public static String fillCode(String code, int targetLength) {

        // Calcula cuántos ceros se deben agregar
        int zerosToAdd = targetLength - code.length();

        // Crea la cadena con ceros a la izquierda
        StringBuilder paddedString = new StringBuilder();
        for (int i = 0; i < zerosToAdd; i++) {
            paddedString.append("0");
        }
        paddedString.append(code); // Agrega la cadena original

        return paddedString.toString();
    }

    private static JSONObject retrieveOutputData(JCoFunction function) throws JCoException {
        // Retrieve output tables
        JCoTable mrpItems = function.getTableParameterList().getTable("MRP_ITEMS");
        JCoTable mrpIndLines = function.getTableParameterList().getTable("MRP_IND_LINES");
        JCoTable mrpTotalLines = function.getTableParameterList().getTable("MRP_TOTAL_LINES");
        JCoTable extensionOut = function.getTableParameterList().getTable("EXTENSIONOUT");

        // Retrieve export parameters
        JCoStructure mrpList = function.getExportParameterList().getStructure("MRP_LIST");
        JCoStructure mrpControlParam = function.getExportParameterList().getStructure("MRP_CONTROL_PARAM");
        JCoStructure mrpStockDetail = function.getExportParameterList().getStructure("MRP_STOCK_DETAIL");
        JCoStructure returnData = function.getExportParameterList().getStructure("RETURN");

        JSONObject result = new JSONObject();

        JSONObject resultTables = new JSONObject();
        JSONObject resultStructures = new JSONObject();

        // Log output data
        JSONObject jsonTablesObject = new JSONObject();

        jsonTablesObject.put("MRP_ITEMS", logTableData(mrpItems, "MRP ITEMS"));
        jsonTablesObject.put("MRP_INDIVIDUAL_LINES", logTableData(mrpIndLines, "MRP INDIVIDUAL LINES"));
        jsonTablesObject.put("MRP_TOTAL_LINES", logTableData(mrpTotalLines, "MRP TOTAL LINES"));
        jsonTablesObject.put("EXTENSION_OUTPUT", logTableData(extensionOut, "EXTENSION OUTPUT"));

        resultTables.put("TABLES", jsonTablesObject);

        JSONObject jsonStructureObject = new JSONObject();
        jsonStructureObject.put("MRP_LIST", logStructureData(mrpList, "MRP LIST"));
        jsonStructureObject.put("MRP_CONTROL_PARAMETERS", logStructureData(mrpControlParam, "MRP CONTROL PARAMETERS"));
        jsonStructureObject.put("MRP_STOCK_DETAIL", logStructureData(mrpStockDetail, "MRP STOCK DETAIL"));
        jsonStructureObject.put("RETURN_DATA", logStructureData(returnData, "RETURN DATA"));

        resultStructures.put("STRUCTURES", jsonStructureObject);

        result.put("TABLES", resultTables);
        result.put("STRUCTURES", resultStructures);

        return result;

    }

    private static JSONArray logStructureData(JCoStructure structure, String structureName) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        for (JCoField field : structure) {
            jsonObject.put(field.getName(), field.getValue());
        }
        jsonArray.put(jsonObject);
        return jsonArray;
    }

    private static JSONArray logTableData(JCoTable table, String tableName) throws JCoException {
        JSONArray jsonArray = new JSONArray();
        if (table.getNumRows() > 0) {
            table.firstRow();
            do {
                JSONObject jsonObject = new JSONObject();
                for (JCoField field : table) {
                    jsonObject.put(field.getName(), field.getValue());
                }
                jsonArray.put(jsonObject);
            } while (table.nextRow());
        }
        return jsonArray;
    }

    public static JSONObject retrieveMaterialGetList(int rowNumber,
            String signMatnr, String optionMatnr,
            String matnrLow, String matnrHigh,
            String signMatShrDesc, String optionMatShrDesc,
            String descrLow, String descrHigh,
            String manuMat, String mfrNo,
            String signPlntSel, String optionPlntSel,
            String plantLow, String plantHigh,
            String signStoLocSel, String optionStoLocSel,
            String stlocLow, String stlocHigh,
            String signSalOrgSel, String optionSalOrgSel,
            String salesOrgLow, String salesOrgHigh,
            String signDisChnSel, String optionDisChnSel,
            String distrChanLow, String distrChanHigh) {
        JSONObject response = new JSONObject();
        try {
            JCoDestination destination = JCoDestinationManager.getDestination("config/conexion");
            JCoFunction function = destination.getRepository().getFunction("BAPI_MATERIAL_GETLIST");

            // Set input parameters
            function.getImportParameterList().setValue("MAXROWS", rowNumber);

            // Set input table parameters 
            JCoTable matnrSelection = function.getTableParameterList().getTable("MATNRSELECTION");
            matnrSelection.appendRow();
            matnrSelection.setValue("SIGN", signMatnr);
            matnrSelection.setValue("OPTION", optionMatnr);
            matnrSelection.setValue("MATNR_LOW", matnrLow);
            matnrSelection.setValue("MATNR_HIGH", matnrHigh);

            JCoTable matShrDescSel = function.getTableParameterList().getTable("MATERIALSHORTDESCSEL");
            matShrDescSel.appendRow();
            matShrDescSel.setValue("SIGN", signMatShrDesc);
            matShrDescSel.setValue("OPTION", optionMatShrDesc);
            matShrDescSel.setValue("DESCR_LOW", descrLow);
            matShrDescSel.setValue("DESCR_HIGH", descrHigh);

            JCoTable manuFacPartNumb = function.getTableParameterList().getTable("MANUFACTURERPARTNUMB");
            manuFacPartNumb.appendRow();
            manuFacPartNumb.setValue("MANU_MAT", manuMat);
            manuFacPartNumb.setValue("MFR_NO", mfrNo);

            JCoTable plntSel = function.getTableParameterList().getTable("PLANTSELECTION");
            plntSel.appendRow();
            plntSel.setValue("SIGN", signPlntSel);
            plntSel.setValue("OPTION", optionPlntSel);
            plntSel.setValue("PLANT_LOW", plantLow);
            plntSel.setValue("PLANT_HIGH", plantHigh);

            JCoTable stoLocSel = function.getTableParameterList().getTable("STORAGELOCATIONSELECT");
            stoLocSel.appendRow();
            stoLocSel.setValue("SIGN", signStoLocSel);
            stoLocSel.setValue("OPTION", optionStoLocSel);
            stoLocSel.setValue("STLOC_LOW", stlocLow);
            stoLocSel.setValue("STLOC_HIGH", stlocHigh);

            JCoTable salOrgSel = function.getTableParameterList().getTable("SALESORGANISATIONSELECTION");
            salOrgSel.appendRow();
            salOrgSel.setValue("SIGN", signSalOrgSel);
            salOrgSel.setValue("OPTION", optionSalOrgSel);
            salOrgSel.setValue("SALESORG_LOW", salesOrgLow);
            salOrgSel.setValue("SALESORG_HIGH", salesOrgHigh);

            JCoTable disChnSel = function.getTableParameterList().getTable("DISTRIBUTIONCHANNELSELECTION");
            disChnSel.appendRow();
            disChnSel.setValue("SIGN", signDisChnSel);
            disChnSel.setValue("OPTION", optionDisChnSel);
            disChnSel.setValue("DISTR_CHAN_LOW", distrChanLow);
            disChnSel.setValue("DISTR_CHAN_HIGH", distrChanHigh);
            // Execute the BAPI
            function.execute(destination);

            // Retrieve output tables
            JCoTable matNrList = function.getTableParameterList().getTable("MATNRLIST");
            JCoTable returnData = function.getTableParameterList().getTable("RETURN");

            // Crear el objeto para los parámetros de entrada
            JSONObject inputParameters = new JSONObject();
            inputParameters.put("MAXROWS", rowNumber);

            // Crear tabla MATNRSELECTION
            JSONArray matnrSelectionArray = new JSONArray();
            JSONObject jsonMatnrSelection = new JSONObject();
            jsonMatnrSelection.put("SIGN", signMatnr);
            jsonMatnrSelection.put("OPTION", optionMatnr);
            jsonMatnrSelection.put("MATNR_LOW", matnrLow);
            jsonMatnrSelection.put("MATNR_HIGH", matnrHigh);
            matnrSelectionArray.put(jsonMatnrSelection);
            inputParameters.put("MATNRSELECTION", matnrSelectionArray);

            // Crear tabla MATERIALSHORTDESCSEL
            JSONArray matShrDescSelArray = new JSONArray();
            JSONObject jsonMatShrDescSel = new JSONObject();
            jsonMatShrDescSel.put("SIGN", signMatShrDesc);
            jsonMatShrDescSel.put("OPTION", optionMatShrDesc);
            jsonMatShrDescSel.put("DESCR_LOW", descrLow);
            jsonMatShrDescSel.put("DESCR_HIGH", descrHigh);
            matShrDescSelArray.put(jsonMatShrDescSel);
            inputParameters.put("MATERIALSHORTDESCSEL", matShrDescSelArray);

            // Crear tabla MANUFACTURERPARTNUMB
            JSONArray manuFacPartNumbArray = new JSONArray();
            JSONObject jsonManuFacPartNumb = new JSONObject();
            jsonManuFacPartNumb.put("MANU_MAT", manuMat);
            jsonManuFacPartNumb.put("MFR_NO", mfrNo);
            manuFacPartNumbArray.put(jsonManuFacPartNumb);
            inputParameters.put("MANUFACTURERPARTNUMB", manuFacPartNumbArray);

            // Crear tabla PLANTSELECTION
            JSONArray plntSelArray = new JSONArray();
            JSONObject jsonPlntSel = new JSONObject();
            jsonPlntSel.put("SIGN", signPlntSel);
            jsonPlntSel.put("OPTION", optionPlntSel);
            jsonPlntSel.put("PLANT_LOW", plantLow);
            jsonPlntSel.put("PLANT_HIGH", plantHigh);
            plntSelArray.put(jsonPlntSel);
            inputParameters.put("PLANTSELECTION", plntSelArray);

            // Crear tabla STORAGELOCATIONSELECT
            JSONArray stoLocSelArray = new JSONArray();
            JSONObject jsonStoLocSel = new JSONObject();
            jsonStoLocSel.put("SIGN", signStoLocSel);
            jsonStoLocSel.put("OPTION", optionStoLocSel);
            jsonStoLocSel.put("STLOC_LOW", stlocLow);
            jsonStoLocSel.put("STLOC_HIGH", stlocHigh);
            stoLocSelArray.put(jsonStoLocSel);
            inputParameters.put("STORAGELOCATIONSELECT", stoLocSelArray);

            // Crear tabla SALESORGANISATIONSELECTION
            JSONArray salOrgSelArray = new JSONArray();
            JSONObject jsonSalOrgSel = new JSONObject();
            jsonSalOrgSel.put("SIGN", signSalOrgSel);
            jsonSalOrgSel.put("OPTION", optionSalOrgSel);
            jsonSalOrgSel.put("SALESORG_LOW", salesOrgLow);
            jsonSalOrgSel.put("SALESORG_HIGH", salesOrgHigh);
            salOrgSelArray.put(jsonSalOrgSel);
            inputParameters.put("SALESORGANISATIONSELECTION", salOrgSelArray);

            // Crear tabla DISTRIBUTIONCHANNELSELECTION
            JSONArray disChnSelArray = new JSONArray();
            JSONObject jsonDisChnSel = new JSONObject();
            jsonDisChnSel.put("SIGN", signDisChnSel);
            jsonDisChnSel.put("OPTION", optionDisChnSel);
            jsonDisChnSel.put("DISTR_CHAN_LOW", distrChanLow);
            jsonDisChnSel.put("DISTR_CHAN_HIGH", distrChanHigh);
            disChnSelArray.put(jsonDisChnSel);
            inputParameters.put("DISTRIBUTIONCHANNELSELECTION", disChnSelArray);

            response.put("PARAMETERS", inputParameters);

            JSONObject tableObjet = new JSONObject();

            tableObjet.put("MATNRLIST", logTableData(matNrList, "MATNRLIST"));
            tableObjet.put("RETURN", logTableData(returnData, "RETURN"));

            response.put("RESULT", tableObjet);

        } catch (JCoException e) {
            JSONObject error = new JSONObject();
            error.put("ERROR_MESSAGE", e.getMessage());
//            error.put("ERROR_STACK_TRACE", e.printStackTrace());
            response.put("ERROR", error);
        }
        return response;
    }

}
