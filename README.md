# ETable

Simple application to evaluate Excel-like tables. Maximum table size limited to 25
columns and 9 rows.

### Expressions

Expressions do not have operator precedence. All operators `+`, `-`, `*` and `/`
evaluate from left to right.

### Values

Cell can contains one of value types:

* number - integer value without sign;
* string - string value
* expression - simple expression with numbers, references to other cells and operations.

### References

Cells in the table can be accessed through reference by two different ways by reference
(string with one letter and one digit) and by column, row pair. In string representation
reference "A1" corresponds to cell with row 0 and column 0. 

### Usage

    java -jar etable-${version}.jar

### License

All source code available under MIT License.
