import java.util.Random
import ofc4j.model.Chart
import ofc4j.model.axis.YAxis
import ofc4j.model.elements.LineChart;

def data1 = new ArrayList<Number>();
def data2 = new ArrayList<Number>();
def data3 = new ArrayList<Number>(); 
def rand = new Random();

for(int i=0; i<9; i++ ) {
    data1.add(1 + rand.nextInt(5));
    data2.add(7 + rand.nextInt(5));
    data3.add(14 + rand.nextInt(5));
}

return new Chart("Three lines example")
    .setYAxis(new YAxis()
        .setRange(0, 20, 5))
    .addElements(
        new LineChart(LineChart.Style.DOT)
            .setWidth(4)
            .setColour("#DFC329")
            .setDotSize(5)
            .setText("aa")
            .addValues(data1),
        new LineChart(LineChart.Style.HOLLOW)
            .setWidth(1)
            .setColour("#6363AC")
            .setDotSize(5)
            .setText("bb")
            .addValues(data2),
        new LineChart()
            .setWidth(1)
            .setColour("#5E4725")
            .setDotSize(5)
            .setText("cc")
            .addValues(data3));