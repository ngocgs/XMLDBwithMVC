<%-- 
    Document   : product
    Created on : Jan 9, 2019, 10:14:57 AM
    Author     : nguye
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<Products>
    <c:forEach var="product" items="${products}">
        <Product>
        <ProductID>${product.productID}</ProductID>
        <ProductName>${product.productName}</ProductName>
        <SupplierID>${product.supplierID}</SupplierID>
        <CategoryID>${product.categoryID}</CategoryID>
        <QuantityPerUnit>${product.quantityPerUnit}</QuantityPerUnit>
        <UnitPrice>${product.unitPrice}</UnitPrice>
        <UnitsInStock>${product.unitsInStock}</UnitsInStock>
        <UnitsOnOrder>${product.unitsOnOrder}</UnitsOnOrder>
        <ReorderLevel>${product.reorderLevel}</ReorderLevel>
        <Discontinued>${product.discontinued}</Discontinued>
    </c:forEach>
</Products>
