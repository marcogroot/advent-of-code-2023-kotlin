#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <unordered_set>
#include <utility>
#include<set>
#include<vector>
using namespace std;

int main() {
    // Replace "yourfilename.txt" with the actual name of your text file
    string filename = "somefile.txt";
    set<pair<long long, long long>> s;
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
        long long number1, number2;

        // Try to extract two numbers from the string stream
        if (iss >> number1 >> number2) {
            // Process the numbers as needed
            pair<long long, long long> p = {number1, number2};
            s.insert(p);

        } else {
            // Handle the case where the line doesn't have two valid numbers
            std::cerr << "Error parsing line: " << line << std::endl;
        }
    }

    cout << "set done" << endl;

    vector<pair<long long, long long>> v1;
    for (auto& x : s) {
        v1.push_back(x);
    }
    cout << "made vector 1" << endl;
    vector<pair<long long, long long>> v2;
    for (long long i = 1; i < v1.size(); i++) {
        v2.push_back(v1[i]);
    }
    cout << "made vector 2" << endl;
    long long sum = 0;
    
    for (long long i = 0 ; i < v1.size(); i++) {
        if (i % 10000 == 0) {
            cout << "boop\n";
        }
        pair<long long, long long> a = v1[i];
        pair<long long, long long> b = v2[i];
        sum += a.second * b.first - a.first * b.second;
    }
    cout << sum << endl;
    sum /= 2;

    cout << sum << endl;
    
    
    
    cout << s.size() << endl;
    // Close the file
    inputFile.close();

    return 0; // Return success
}