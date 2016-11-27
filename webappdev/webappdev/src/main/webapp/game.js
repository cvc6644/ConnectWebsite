/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//document.getElementById('GameForMe').re
var ss = document.getElementById("GameForMe").lastChild;
var svgns = "http://www.w3.org/2000/svg";
var svg = document.createElement("p");
svg.innerHTML="hello world";
svg.onclick = ballstothewall;
ss.appendChild(svg);