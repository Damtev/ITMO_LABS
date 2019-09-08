cmake_minimum_required(VERSION 3.13)
project(LabASD5_MinCostFlow)

set(CMAKE_CXX_STANDARD 14)

set(BUILD_A A_MinCost.cpp)
set(SOURCE_FILES_A A_MinCost.cpp)
add_executable(${BUILD_A} ${SOURCE_FILES_A})

set(BUILD_B B_HungaryAlgorithm.cpp)
set(SOURCE_FILES_B B_HungaryAlgorithm.cpp)
add_executable(${BUILD_B} ${SOURCE_FILES_B})

set(BUILD_C C_CostlyLabels.cpp)
set(SOURCE_FILES_C C_CostlyLabels.cpp)
add_executable(${BUILD_C} ${SOURCE_FILES_C})

set(BUILD_D D_Game.cpp)
set(SOURCE_FILES_D D_Game.cpp)
add_executable(${BUILD_D} ${SOURCE_FILES_D})

set(BUILD_E E_Salesmen.cpp)
set(SOURCE_FILES_E E_Salesmen.cpp)
add_executable(${BUILD_E} ${SOURCE_FILES_E})