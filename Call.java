import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
class Call {
    private HttpResponse<String> response;
    private HttpResponse<String> response1;
    private HttpResponse<String> response2;

    public void get_data(double latitude, double longitude) {
        String apiKey = "a95ac20b44385c921f020bdcf01d1094";
//        double latitude = 32.166351;
//        double longitude = 74.195900;

        String url = "https://api.openweathermap.org/data/2.5/weather"
                + "?lat=" + latitude
                + "&lon=" + longitude
                + "&appid=" + apiKey;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            //   System.out.println(response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        store_data();
    }

    private void store_data() {
        File f1 = new File("data.txt");
        try {
            FileWriter fw1 = new FileWriter("data.txt", true);
            fw1.write(response.body());
            fw1.write("\n");
            fw1.write("\n");
            fw1.flush();
            fw1.close();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void get_forcast_data(double latitude, double longitude) {
        String apiKey = "a95ac20b44385c921f020bdcf01d1094";
//        double latitude = 32.166351;
//        double longitude = 74.195900;

        String url = "https://api.openweathermap.org/data/2.5/forecast"
                + "?lat=" + latitude
                + "&lon=" + longitude
                + "&appid=" + apiKey;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            response1 = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            //   System.out.println(response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        store_forecast_data();
    }

    private void store_forecast_data() {
        File f2 = new File("forecast.txt");
        try {
            FileWriter fw2 = new FileWriter("forecast.txt", true);
            fw2.write(response1.body());
            fw2.write("\n");
            fw2.write("\n");
            fw2.flush();
            fw2.close();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void get_airpollution_data(double latitude, double longitude) {
        String apiKey = "a95ac20b44385c921f020bdcf01d1094";
//        double latitude = 32.166351;
//        double longitude = 74.195900;

        String url = "https://api.openweathermap.org/data/2.5/air_pollution"
                + "?lat=" + latitude
                + "&lon=" + longitude
                + "&appid=" + apiKey;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            response2 = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            //   System.out.println(response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        store_pollution_data();
    }

    void store_pollution_data() {
        File f3 = new File("pollution.txt");
        try {
            FileWriter fw3 = new FileWriter("pollution.txt", true);
            fw3.write(response2.body());
            fw3.write("\n");
            fw3.write("\n");
            fw3.flush();
            fw3.close();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    void check_weather(double lat, double lon) {
        boolean flag = false;
        try {
            BufferedReader br = new BufferedReader(new FileReader("data.txt"));
            String line;
            Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
            Matcher matcher;
            while ((line = br.readLine()) != null) {
                matcher = pattern.matcher(line);
                while (matcher.find()) {
                    double number = Double.parseDouble(matcher.group());
                    if (lon == number) {
                        flag = true;

                    }
                    if (lat == number) {
                        flag = true;
                        break;
                    } else {
                        flag = false;
                    }

                }
                if (flag == true) {
                    System.out.println(line);
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

//    void check_weather_name(String name) {
//        int num = 0;
//        try {
//            BufferedReader br1 = new BufferedReader(new FileReader("data.txt"));
//            String line;
//            while ((line = br1.readLine()) != null) {
//                //num=line.indexOf(name);
//                if (line.contains(name)) {
//                    System.out.println(line);
//                    break;
//                }
//            }
//        } catch (IOException e) {
//            e.getMessage();
//        }
//    }
void check_weather_name(String name){
    try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
        String line;
        StringBuilder jsonData = new StringBuilder();
        while ((line = br.readLine()) != null) {
            if(line.contains(name)){
                jsonData.append(line);}
        }

        // Find the index of "sunrise" key
        int startIndex = jsonData.indexOf("\"temp\"");

        // If "sunrise" key is found
        if (startIndex != -1) {
            // Find the index of the value associated with "sunrise" key
            int valueStartIndex = jsonData.indexOf(":", startIndex) + 1;
            int valueEndIndex = jsonData.indexOf(",", valueStartIndex);
            if (valueEndIndex == -1) {
                valueEndIndex = jsonData.indexOf("}", valueStartIndex);
            }

            // Extract the value associated with "sunrise" key
            String sunriseValue = jsonData.substring(valueStartIndex, valueEndIndex).trim();

            // Print the key and its value
            System.out.println("Temp: " + sunriseValue);
        } else {
            System.out.println("Key 'temp' not found in the JSON data.");
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
        String line;
        StringBuilder jsonData = new StringBuilder();
        while ((line = br.readLine()) != null) {
            if(line.contains(name)){
                jsonData.append(line);}
        }

        // Find the index of "sunrise" key
        int startIndex = jsonData.indexOf("\"pressure\"");

        // If "sunrise" key is found
        if (startIndex != -1) {
            // Find the index of the value associated with "sunrise" key
            int valueStartIndex = jsonData.indexOf(":", startIndex) + 1;
            int valueEndIndex = jsonData.indexOf(",", valueStartIndex);
            if (valueEndIndex == -1) {
                valueEndIndex = jsonData.indexOf("}", valueStartIndex);
            }

            // Extract the value associated with "sunrise" key
            String sunriseValue = jsonData.substring(valueStartIndex, valueEndIndex).trim();

            // Print the key and its value
            System.out.println("Pressure: " + sunriseValue);
        } else {
            System.out.println("Key 'temp' not found in the JSON data.");
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
        String line;
        StringBuilder jsonData = new StringBuilder();
        while ((line = br.readLine()) != null) {
            if(line.contains(name)){
                jsonData.append(line);}
        }

        // Find the index of "sunrise" key
        int startIndex = jsonData.indexOf("\"humidity\"");

        // If "sunrise" key is found
        if (startIndex != -1) {
            // Find the index of the value associated with "sunrise" key
            int valueStartIndex = jsonData.indexOf(":", startIndex) + 1;
            int valueEndIndex = jsonData.indexOf(",", valueStartIndex);
            if (valueEndIndex == -1) {
                valueEndIndex = jsonData.indexOf("}", valueStartIndex);
            }

            // Extract the value associated with "sunrise" key
            String sunriseValue = jsonData.substring(valueStartIndex, valueEndIndex).trim();

            // Print the key and its value
            System.out.println("Humidity: " + sunriseValue);
        } else {
            System.out.println("Key 'temp' not found in the JSON data.");
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

}
    void get_sunrise_sunset(String name) {
        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            String line;
            StringBuilder jsonData = new StringBuilder();
            while ((line = br.readLine()) != null) {
              if(line.contains(name)){
                jsonData.append(line);}
            }
            // Find the index of "sunrise" key
            int startIndex = jsonData.indexOf("\"sunrise\"");

            // If "sunrise" key is found
            if (startIndex != -1) {
                // Find the index of the value associated with "sunrise" key
                int valueStartIndex = jsonData.indexOf(":", startIndex) + 1;
                int valueEndIndex = jsonData.indexOf(",", valueStartIndex);
                if (valueEndIndex == -1) {
                    valueEndIndex = jsonData.indexOf("}", valueStartIndex);
                }

                // Extract the value associated with "sunrise" key
                String sunriseValue = jsonData.substring(valueStartIndex, valueEndIndex).trim();

                // Print the key and its value
                System.out.println("Sunrise: " + sunriseValue);
            } else {
                System.out.println("Key 'sunrise' not found in the JSON data.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            String line;
            StringBuilder jsonData = new StringBuilder();
            while ((line = br.readLine()) != null) {
                if(line.contains(name)){
                jsonData.append(line);}
            }

            // Find the index of "sunrise" key
            int startIndex = jsonData.indexOf("\"sunset\"");


            if (startIndex != -1) {
                // Find the index of the value associated with "sunrise" key
                int valueStartIndex = jsonData.indexOf(":", startIndex) + 1;
                int valueEndIndex = jsonData.indexOf(",", valueStartIndex);
                if (valueEndIndex == -1) {
                    valueEndIndex = jsonData.indexOf("}", valueStartIndex);
                }

                // Extract the value associated with "sunrise" key
                String sunriseValue = jsonData.substring(valueStartIndex, valueEndIndex).trim();

                // Print the key and its value
                System.out.println("Sunset: " + sunriseValue);
            } else {
                System.out.println("Key 'sunrise' not found in the JSON data.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void show_forecast(String name) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("forecast.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(name)) {
                    System.out.println(line);
                }
            }

        } catch (IOException e) {
            e.getMessage();
        }
    }

    void show_dialogue(String name) {
        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            String line;
            StringBuilder jsonData = new StringBuilder();
            while ((line = br.readLine()) != null) {
              if(line.contains(name)){
                jsonData.append(line);}
            }

            // Find the index of "sunrise" key
            int startIndex = jsonData.indexOf("\"feels_like\"");

            // If "sunrise" key is found
            if (startIndex != -1) {
                // Find the index of the value associated with "sunrise" key
                int valueStartIndex = jsonData.indexOf(":", startIndex) + 1;
                int valueEndIndex = jsonData.indexOf(",", valueStartIndex);
                if (valueEndIndex == -1) {
                    valueEndIndex = jsonData.indexOf("}", valueStartIndex);
                }

                // Extract the value associated with "sunrise" key
                String sunriseValue = jsonData.substring(valueStartIndex, valueEndIndex).trim();

                // Print the key and its value
                System.out.println("Feelslike: " + sunriseValue);
            } else {
                System.out.println("Key 'Feels like' not found in the JSON data.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            String line;
            StringBuilder jsonData = new StringBuilder();
            while ((line = br.readLine()) != null) {
                if(line.contains(name)){
                    jsonData.append(line);}
            }

            // Find the index of "sunrise" key
            int startIndex = jsonData.indexOf("\"temp_min\"");

            // If "sunrise" key is found
            if (startIndex != -1) {
                // Find the index of the value associated with "sunrise" key
                int valueStartIndex = jsonData.indexOf(":", startIndex) + 1;
                int valueEndIndex = jsonData.indexOf(",", valueStartIndex);
                if (valueEndIndex == -1) {
                    valueEndIndex = jsonData.indexOf("}", valueStartIndex);
                }

                // Extract the value associated with "sunrise" key
                String sunriseValue = jsonData.substring(valueStartIndex, valueEndIndex).trim();

                // Print the key and its value
                System.out.println("Min Temp: " + sunriseValue);
            } else {
                System.out.println("Key 'Feels like' not found in the JSON data.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            String line;
            StringBuilder jsonData = new StringBuilder();
            while ((line = br.readLine()) != null) {
                if(line.contains(name)){
                    jsonData.append(line);}
            }

            // Find the index of "sunrise" key
            int startIndex = jsonData.indexOf("\"temp_max\"");

            // If "sunrise" key is found
            if (startIndex != -1) {
                // Find the index of the value associated with "sunrise" key
                int valueStartIndex = jsonData.indexOf(":", startIndex) + 1;
                int valueEndIndex = jsonData.indexOf(",", valueStartIndex);
                if (valueEndIndex == -1) {
                    valueEndIndex = jsonData.indexOf("}", valueStartIndex);
                }

                // Extract the value associated with "sunrise" key
                String sunriseValue = jsonData.substring(valueStartIndex, valueEndIndex).trim();

                // Print the key and its value
                System.out.println("Max Temp: " + sunriseValue);
            } else {
                System.out.println("Key 'Feels like' not found in the JSON data.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class Main {

    
    public static void main(String[] args) {
        Call c1 = new Call();
        Scanner s1=new Scanner(System.in);
//        System.out.println("Enter latitude =");
//        double lat=s1.nextDouble();
//        System.out.println("Enter longitude =");
//        double longitude=s1.nextDouble();
//        c1.get_data(lat,longitude);
//        c1.get_forcast_data(lat,longitude);
//        c1.get_airpollution_data(lat,longitude);
        System.out.println("Enter city name =");
        String name=s1.nextLine();
        //c1.check_weather(lat,longitude);
        c1.check_weather_name(name);
        //c1.get_sunrise_sunset(name);
        //c1.show_forecast(name);
       // c1.show_dialogue(name);
    }
}
