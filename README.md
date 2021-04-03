# UPDATE 4th APRIL

The Spreadsheets service API will be updated very soon...

The change concerns how spreadsheet values (raw and computed values) are stored and transferred.
Instead of `List<List<String>>`, the new format will be `String[][]`.

Consequently, the updated version of `getSpreadsheetValues(...)`, will be:
    `String[][] getSpreadsheetValues(@PathParam("sheetId") String sheetId, @QueryParam("userId") String userId, @QueryParam("password") String password);

The `Spreadsheet` class received the same change and now stores its raw values in a String[rows][cols] member.

As part of the update, the **preferred** way to update the raw value of a cell in the `Spreadsheet` class uses the following method,
which takes the string name of the cell (e.g., A1), instead of row, column indices.

  `public void setCellRawValue(String cell, String value)`

Also included in the update is the `CellRange` utility class. This class includes the helper method `extractRangeValuesFrom`,
which should be considered in the implementation of the `importrange` formula.

### NOTE: these changes can be previewed in the apiv2 branch of the repository.

---
## API for SD project 2020/21
Web page: [https://preguica.github.io/sd2021/praticas2021/trab1/trab1](https://preguica.github.io/sd2021/praticas2021/trab1/trab1)

**Do not forget to replace the student numbers in the pom.xml**

### **Notes:**
* SOAP interfaces to be added.

### **Version:** 0.9 - 26/mar - 23:00
Contents:
* API interfaces for REST
* Engine for computing values: check comments on [tp1.impl.engine.SpreadsheetEngineImpl](https://github.com/smduarte/sd2021-tp1-api/blob/main/sd2021-tp1-api/src/tp1/impl/engine/SpreadsheetEngineImpl.java) for instructions on how to use it. A new version of this class may be provided at a later time.
