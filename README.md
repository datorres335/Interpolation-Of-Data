# Interpolating Polynomials Using Newton's and Lagrange's Methods

## Overview
This program reads a set of data points from a **text file**, constructs a **divided difference table**, and generates the interpolating polynomial using:
- **Newton's Form**
- **Lagrange's Form**
- **Simplified Polynomial Form**

## Implementation Details
This program is implemented in **Java** using two main files:
1. **Main.java** - Reads input, constructs the divided difference table, and generates polynomial representations.
2. **LagrangeNumeratorCoef.java** - Handles computations for Lagrange interpolation coefficients.

## Input Format
The user provides a **text file** containing `x` and `f(x)` values. The program reads this file and extracts the necessary data.

### **Example Input File**
```
1 3
1.5 13/4
0 3
2 5/3
```
This represents the data points:
- **(1,3)**
- **(1.5,13/4)**
- **(0,3)**
- **(2,5/3)**

## Program Output
1. **Divided Difference Table**
2. **Interpolating polynomial in Newton's Form**
3. **Interpolating polynomial in Lagrange's Form**
4. **Simplified polynomial representation**

## Example Output
### **Divided Difference Table**
```
x                        f[]                    f[ , ]                 f[ , , ]                f[ , , , ]

1                         3                        

                                                      1/2    

3/2                  13/4                                                1/3

                                                      1/6                                                  -2

 0                      3                                                       -5/3

                                                      -2/3

 2                      5/3  
```

### **Newton's Interpolating Polynomial**
```
3 + 1/2(x-1) +1/3(x-1)(x-3/2) - 2(x-1)(x-3/2)x
```

### **Lagrange's Interpolating Polynomial**
```
6(x-3/2)(x)(x-2) - 26/3(x-1)(x)(x-2) - (x-1)(x-3/2)(x-2) + 5/3(x-1)(x-3/2)(x)
```

### **Simplified Polynomial Form**
```
-2x^3 + 5.334x^2 – 3.334x + 3
```

## Java Code Structure
### **Main.java**
- **Reads the text file** containing `x` and `f(x)` values.
- **Constructs and displays the divided difference table**.
- **Computes Newton’s and Lagrange’s polynomials**.
- **Generates the simplified polynomial**.

### **LagrangeNumeratorCoef.java**
- Computes **Lagrange interpolation coefficients**.
- Handles **combination calculations** for simplification.

## Usage Instructions
1. **Compile the program**:
   ```bash
   javac Main.java LagrangeNumeratorCoef.java
   ```
2. **Run the program**:
   ```bash
   java Main
   ```
3. **Enter the path** to a text file containing `x` and `f(x)` values.
4. **View the generated polynomials** in Newton's, Lagrange's, and simplified forms.
