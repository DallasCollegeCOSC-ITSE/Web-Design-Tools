<?php
//**************************************************************
// Given the length and width of a rectangle, this php program
// computes and outputs the perimeter and area of the rectangle.
//**************************************************************

$length;
$width;
$area;
$perimeter;

echo ("Program to compute and output the 
perimeter and area of a rectangle.")."<div/>";

$length = 6.0;
$width=4.0;
$perimeter=2*($length+$width);
$area = $length *$width;

echo("Length = {$length}")."<div/>";
echo("Width = {$width}")."<div/>";
echo("Perimeter = {$perimeter}")."<div/>";
echo("Area = {$area}")."<div/>";


?>
