function compute()
{
    var p = document.getElementById("principal").value;
    var principal = parseInt(p);
    var rate = document.getElementById("rate").value;
    var years = document.getElementById("years").value;
    var interest = principal * years * rate /100;
    var amount = principal + interest;
    var result = document.getElementById("result");

    var year = new Date().getFullYear()+parseInt(years);
    
    if (principal <=0) {
        alert("Please enter a positive number");
        document.getElementById("principal").focus();
    }else{
        result.innerHTML = "If you deposit <mark>$" + principal + "</mark>,<br>" +
                           "at an interest rate of <mark>$" + rate + "%</mark>.<br>" +
                           "You will receive an amount of <mark>$" + amount + "</mark>,<br>" +
                           "in the year <mark>" + year + "</mark>.<br>";
    }

}

function updateRate()
{
    var rateval = document.getElementById("rate").value;
    document.getElementById("rateval").textContent=rateval+"%";
}