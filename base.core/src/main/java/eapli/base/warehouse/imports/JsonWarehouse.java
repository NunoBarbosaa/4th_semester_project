package eapli.base.warehouse.imports;

import eapli.base.utils.Description;
import eapli.base.utils.Measurement;
import eapli.base.utils.Unit;
import eapli.base.warehouse.domain.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class JsonWarehouse {
    public static JSONObject parseJsonFile(FileReader file) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(file);
        return (JSONObject) obj;
    }

    public static Warehouse createWarehouse(JSONObject json) {
        final String warehouseDescription = (String) json.get("Warehouse");
        final double warehouseLength = Double.parseDouble(String.valueOf(json.get("Length")));
        final double warehouseWidth = Double.parseDouble(String.valueOf(json.get("Width")));
        final double warehouseSquare = Double.parseDouble(String.valueOf(json.get("Square")));
        final Unit unit= selectUnit((String) json.get("Unit"));

        return new Warehouse(new Description(warehouseDescription), new Measurement(warehouseWidth, unit), new Measurement(warehouseLength, unit),
                new Measurement(warehouseSquare, unit));
    }

    public static void createAisles(JSONObject json, Warehouse warehouse){
        JSONArray aisleArray = (JSONArray) json.get("Aisles");

        for (Object o : aisleArray) {
            JSONObject aisleObject = (JSONObject) o;

            int id = Integer.parseInt(aisleObject.get("Id").toString());

            Square beginSquare = createSquare((JSONObject) aisleObject.get("begin"));
            Square endSquare = createSquare((JSONObject) aisleObject.get("end"));
            Square depthSquare = createSquare((JSONObject) aisleObject.get("depth"));

            Accessibility access = selectAccess(aisleObject.get("accessibility").toString());

            Aisle aisle = new Aisle(id, beginSquare, endSquare, depthSquare, access);
            createRow(aisle, aisleObject);
            warehouse.addAisle(aisle);
        }

    }

    private static void createRow(Aisle aisle, JSONObject aisleObject) {
        JSONArray rowArray = (JSONArray) aisleObject.get("rows");

        for (Object o : rowArray){
            JSONObject rowObject = (JSONObject) o;

            int id = Integer.parseInt(rowObject.get("Id").toString());

            Square beginSquare = createSquare((JSONObject) rowObject.get("begin"));
            Square endSquare = createSquare((JSONObject) rowObject.get("end"));

            int shelfCount = Integer.parseInt(rowObject.get("shelves").toString());

            AisleRow row = new AisleRow(id, beginSquare, endSquare, shelfCount);

            aisle.addRow(row);
        }
    }

    public static void createAgvDock(JSONObject jsonObject, Warehouse warehouse) {
        JSONArray docksArray = (JSONArray) jsonObject.get("AGVDocks");

        for (Object o : docksArray) {
            JSONObject dockObject = (JSONObject) o;

            String id = dockObject.get("Id").toString();

            Square beginSquare = createSquare((JSONObject) dockObject.get("begin"));
            Square endSquare = createSquare((JSONObject) dockObject.get("end"));
            Square depthSquare = createSquare((JSONObject) dockObject.get("depth"));

            Accessibility access = selectAccess(dockObject.get("accessibility").toString());

            AgvDock dock = new AgvDock(id, beginSquare, endSquare, depthSquare, access);


            warehouse.addDock(dock);
        }
    }

    private static Square createSquare(JSONObject obj) {
        return new Square(Integer.parseInt(obj.get("lsquare").toString()), Integer.parseInt(obj.get("wsquare").toString()));
    }

    private static Unit selectUnit(String unit) {
        if(unit.equalsIgnoreCase("mm"))
            return Unit.MILLIMETER;
        if(unit.equalsIgnoreCase("cm"))
            return Unit.CENTIMETER;
        if(unit.equalsIgnoreCase("m"))
            return Unit.METER;
        if(unit.equalsIgnoreCase("km"))
            return Unit.KILOMETER;
        else
            throw new IllegalArgumentException("Measurement unit not found");
    }

    private static Accessibility selectAccess(String access) {
        if(access.equalsIgnoreCase("w-"))
            return Accessibility.W_MINUS;
        if(access.equalsIgnoreCase("w+"))
            return Accessibility.W_PLUS;
        if(access.equalsIgnoreCase("l-"))
            return Accessibility.L_MINUS;
        if(access.equalsIgnoreCase("l+"))
            return Accessibility.L_PLUS;
        else
            throw new IllegalArgumentException("Invalid access direction");
    }


}
