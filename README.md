# ETable

Simple application to evaluate Excel-like tables.

### Table cells

Expressions do not have operator precedence. All operators `+`, `-`, `*` and `/`
evaluate from left to right.

Cell can contains one of value types:

* number - integer value without sing;
* string - string value
* expression - simple expression with numbers, references to other cells and operations.


### Usage

    java -jar etable-${version}.jar

### License

All source code available under MIT License.
