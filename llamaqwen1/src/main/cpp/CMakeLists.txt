# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html.
# For more examples on how to use CMake, see https://github.com/android/ndk-samples.

# Sets the minimum CMake version required for this project.
cmake_minimum_required(VERSION 3.22.1)

# Declares the project name. The project name can be accessed via ${ PROJECT_NAME},
# Since this is the top level CMakeLists.txt, the project name is also accessible
# with ${CMAKE_PROJECT_NAME} (both CMake variables are in-sync within the top level
# build script scope).
project("llamaqwen")

#add_subdirectory(llama.cpp)
set(HAS_LLAMA_CPP OFF)

add_library(${CMAKE_PROJECT_NAME} SHARED
        # List C/C++ source files with relative paths to this CMakeLists.txt.
        llamaqwen.cpp)

target_link_libraries(${CMAKE_PROJECT_NAME}
        # List libraries link to the target library
        android
        log)

if (HAS_LLAMA_CPP)
    target_compile_definitions(${CMAKE_PROJECT_NAME} PRIVATE HAS_LLAMA_CPP=1)
    include(FetchContent)
    FetchContent_Declare(
            llama
            GIT_REPOSITORY https://github.com/ggerganov/llama.cpp
            GIT_TAG master
    )

    # Also provides "common"
    FetchContent_MakeAvailable(llama)


    target_link_libraries(${CMAKE_PROJECT_NAME}
            # List libraries link to the target library
            llama
            common)
endif ()