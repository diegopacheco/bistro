package org.conceptoriented.bistro.examples;

import org.conceptoriented.bistro.core.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ExUtils {

    public static Table readFromCsv(Schema schema, String location, String fileName) throws FileNotFoundException {
        String filePath = location + "/" + fileName;
        List<String[]> lines = readLinesFromCsvFile(filePath);
        Table table = schema.createTable(fileName);
        Column[] columns = createColumns(schema, table, lines.get(0));
        importData(table, columns, lines);
        return table;
    }

    private static void importData(Table table, Column[] columns, List<String[]> lines) {
        for(int i=0; i<lines.size()-1; i++) {
            table.add();
            for(int j=0; j<columns.length; j++) {
                columns[j].setValue(i, lines.get(i+1)[j]);
            }
        }
    }

    private static Column[] createColumns(Schema schema, Table table, String[] columnNames) {
        Table columnType = schema.getTable("Object");
        Column[] columns = new Column[columnNames.length];
        for(int i=0; i<columnNames.length; i++) {
            columns[i] = schema.createColumn(columnNames[i], table, columnType);
        }
        return columns;
    }

    private static List<String[]> readLinesFromCsvFile(String path) throws FileNotFoundException {
        List<String[]> lines = new ArrayList<>();
        Scanner scanner = new Scanner(new File(path));
        while (scanner.hasNextLine())
        {
            String line = scanner.nextLine();
            String[] fields = line.split(",");
            lines.add(fields);
        }
        return lines;
    }

}
