#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <unordered_set>
#include <utility>
#include<set>
using namespace std;

int main() {
    // Replace "yourfilename.txt" with the actual name of your text file
    string filename = "somefile.txt";
    set<pair<int, int>> s;
    // Create an >input file stream object
    std::ifstream inputFile(filename);

    // Check if the file is open
    if (!inputFile.is_open()) {
        std::cerr << "Error opening file: " << filename << std::endl;
        return 1; // Return an error code
    }

    // Read the file line by line
    std::string line;
    while (std::getline(inputFile, line)) {
        // Create an input string stream from the current line
        std::istringstream iss(line);

        // Variables to store the two numbers
        int number1, number2;

        // Try to extract two numbers from the string stream
        if (iss >> number1 >> number2) {
            // Process the numbers as needed
            pair<int, int> p = {number1, number2};
            s.insert(p);

        } else {
            // Handle the case where the line doesn't have two valid numbers
            std::cerr << "Error parsing line: " << line << std::endl;
        }
    }
    ofstream myfile;
    myfile.open ("outputFile.txt");
    for (pair<int, int> x : s) {
        myfile << x.first << " " << x.second << "\n";
    }
    myfile.close();
    cout << s.size() << endl;
    // Close the file
    inputFile.close();

    return 0; // Return success
}