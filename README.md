# CLEOPS Simulator

## Overview

The **CLEOPS Simulator** is an educational tool designed to emulate the **Cleopatra processor**, an 8-bit accumulator-based processor originally developed by Ney Calazans and Fernando Moraes (PUCRS). The simulator is implemented in **Java** and combines a robust backend with an intuitive graphical user interface (GUI). It provides a detailed and interactive environment for analyzing the architecture and operation of a simplified processor based on the Von Neumann model.

This project is targeted at students and hardware enthusiasts, offering a high-performance simulation tool for learning the fundamentals of processor operations. It supports assembly programming, code assembly, memory manipulation, and instruction execution.

## Key Features

- **Educational Focus**: Ideal for understanding the fundamentals of processor architecture and operations.
- **Processor Emulation**:
  - **8-bit CISC architecture** with 14 instructions and 4 addressing modes.
  - Simulated memory with 256 bytes, 6 8-bit registers, and 4 status flags.
- **User-Friendly GUI**:
  - Built with Java Swing for simplicity and interactivity.
  - Supports editing, saving, and assembling assembly code through a graphical interface.
- **Comprehensive Instruction Set**:
  - Includes instructions such as `HLT`, `LDA`, `STA`, `ADD`, `OR`, `AND`, and conditional jumps (`JMP`, `JZ`, `JN`, etc.).
- **Advanced Simulation**:
  - Monitor processor state, memory contents, and flags during execution.
  - Handle exceptions gracefully, including invalid instructions or memory access.

## Project Development

The development process included the following milestones:

1. **Research and Understanding**:
   - Detailed study of the Cleopatra processor architecture and its components, including registers, memory, and the instruction set.
2. **Backend Implementation**:
   - Design and implementation of the core simulation logic.
   - Support for loading, assembling, and executing user-provided assembly programs.
   - File manipulation features, including saving and reading `.asm` files.
3. **Graphical User Interface**:
   - Development of an intuitive GUI using Java Swing.
   - Features include line numbering, error display, and memory visualization.
4. **Testing and Debugging**:
   - Extensive testing to ensure accurate simulation and user interaction.
   - Exception handling for invalid inputs or operations.
5. **Documentation**:
   - Comprehensive user guide explaining simulator functionality and usage.

## Example Workflow

1. **Write Code**: Use the GUI editor to write an assembly program.
2. **Assemble**: Compile the program into machine code using the *Assemble* button. Errors will be displayed in the *Messages* area.
3. **Simulate**: Execute the assembled program using the *Run* button. Monitor memory and register states during execution.

### Example Code

```asm
LDA #10       ; Load immediate value 10 into the accumulator
ADD #20       ; Add immediate value 20 to the accumulator
STA 15        ; Store the result at memory address 15
HLT           ; Halt the simulation
```

## Requirements

To run the **CLEOPS Simulator**, ensure your system meets the following requirements:

- **Programming Language**: Java 8 or higher.
- **Development Environment**: Any IDE that supports Java (e.g., IntelliJ IDEA, Eclipse, NetBeans) or a command-line interface with Java installed.
- **Operating System**: Cross-platform compatibility (Windows, Linux, macOS).
- **Dependencies**: None (the simulator is built entirely with Java Swing).

## Installation

Follow these steps to download, build, and run the CLEOPS Simulator:

1. **Clone the Repository**:
   Clone the repository to your local machine using Git:
   ```bash
   git clone https://github.com/brunospies/cleops-simulator.git
   ```
### Compile the Project:

To compile the project from the command line:

```bash
javac -d bin src/*.java
```

### Run the Simulator:

After compiling the project, run the simulator using the following command:

```bash
java -cp bin Main
```
### Using an IDE:

To run the CLEOPS Simulator using an Integrated Development Environment (IDE), follow these steps:

1. Open the project in your preferred IDE (e.g., IntelliJ IDEA, Eclipse, or NetBeans).
2. Build the project to compile all Java files.
3. Locate the `Main` class in the project structure.
4. Run the `Main` class to start the simulator.

The IDE will handle compilation and execution, providing an easy way to debug and interact with the simulator.

## Contributing

Contributions to the **CLEOPS Simulator** are welcome! Follow the steps below to contribute:

1. **Fork the Repository**:
   - Click the "Fork" button at the top of the repository page to create a copy in your GitHub account.

2. **Clone the Repository**:
   - Clone the forked repository to your local machine:
     ```bash
     git clone https://github.com/your-username/cleops-simulator.git
     ```

3. **Create a Feature Branch**:
   - Create a new branch for your feature or bug fix:
     ```bash
     git checkout -b feature/your-feature-name
     ```

4. **Make Changes**:
   - Implement your changes and ensure everything works as intended.
   - Add comments and documentation where necessary.

5. **Commit Your Changes**:
   - Write clear and concise commit messages describing your changes:
     ```bash
     git commit -m "Add a brief description of your changes"
     ```

6. **Push Your Changes**:
   - Push your branch to your forked repository:
     ```bash
     git push origin feature/your-feature-name
     ```

7. **Open a Pull Request**:
   - Navigate to the original repository and click the "New Pull Request" button.
   - Select your branch and provide a detailed description of the changes you made.

### Contribution Guidelines:

- Ensure your code adheres to the project's coding style and standards.
- Test your changes thoroughly to avoid breaking existing functionality.
- Include comments and documentation for new features or changes.
- Be respectful and collaborative when discussing changes in pull requests or issues.

If you have any questions or suggestions, feel free to open an issue or discussion on the repository.

