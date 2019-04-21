cmake_minimum_required(VERSION 3.13)
project(LabASD4_Flow)

set(CMAKE_CXX_STANDARD 17)

set(BUILD_A A_Flow)
set(SOURCE_FILES_A A_Flow.cpp)
add_executable(${BUILD_A} ${SOURCE_FILES_A})

set(BUILD_B B_MinCut)
set(SOURCE_FILES_B B_MinCut.cpp)
add_executable(${BUILD_B} ${SOURCE_FILES_B})

set(BUILD_C C_Snails.cpp)
set(SOURCE_FILES_C C_Snails.cpp)
add_executable(${BUILD_C} ${SOURCE_FILES_C})

set(BUILD_D D_Matching.cpp)
set(SOURCE_FILES_D D_Matching.cpp)
add_executable(${BUILD_D} ${SOURCE_FILES_D})

set(BUILD_F F_Moving.cpp)
set(SOURCE_FILES_F F_Moving.cpp)
add_executable(${BUILD_F} ${SOURCE_FILES_F})

set(BUILD_G G_GreatWall.cpp)
set(SOURCE_FILES_G G_GreatWall.cpp)
add_executable(${BUILD_G} ${SOURCE_FILES_G})

set(BUILD_H H_SpaceTravels.cpp)
set(SOURCE_FILES_H H_SpaceTravels.cpp)
add_executable(${BUILD_H} ${SOURCE_FILES_H})

set(BUILD_I I_Tournament.cpp)
set(SOURCE_FILES_I I_Tournament.cpp)
add_executable(${BUILD_I} ${SOURCE_FILES_I})

set(BUILD_J J_Cockroaches.cpp)
set(SOURCE_FILES_J J_Cockroaches.cpp)
add_executable(${BUILD_J} ${SOURCE_FILES_J})