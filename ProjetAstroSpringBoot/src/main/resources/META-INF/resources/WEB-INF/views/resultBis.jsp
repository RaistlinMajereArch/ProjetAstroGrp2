<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
 <head>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
      google.charts.load("current", {packages:["corechart"]});
      google.charts.setOnLoadCallback(drawChart);
      function drawChart() {
        var data = google.visualization.arrayToDataTable
            ([['X', 'Y'],
              [1, 3],
              [2, 2.5],
              [3, 3],
              [4, 4],
              [5, 4],
              [6, 3],
              [7, 2.5],
              [8, 3]
        ]);

        var options = {
          legend: 'none',
          hAxis: { minValue: 0, maxValue: 9 },
          curveType: 'function',
          pointSize: 20,
        };

        var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
        chart.draw(data, options);
    }
    </script>
  </head>
  <body>
    <div id="chart_div" style="width: 900px; height: 500px;"></div>
  </body>
</html>