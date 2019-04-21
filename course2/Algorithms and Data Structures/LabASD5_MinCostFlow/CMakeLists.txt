cmake_minimum_required(VERSION 3.13)
project(LabASD5_MinCostFlow)

set(CMAKE_CXX_STANDARD 14)

set(BUILD_A A_MinCost.cpp)
set(SOURCE_FILES_A A_MinCost.cpp)
add_executable(${BUILD_A} ${SOURCE_FILES_A})

set(BUILD_B B_HungaryAlgorithm.cpp)
set(SOURCE_FILES_B B_HungaryAlgorithm.cpp)
add_executable(${BUILD_B} ${SOURCE_FILES_B})