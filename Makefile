# Variable
JAVAC = javac

JAVA = java

JAR = jar

SRC_DIR = Source

GUI_DIR = GUI

BIN_DIR = bin

MAIN_CLASS = Main

# Search All Java Files
SOURCES = $(wildcard $(SRC_DIR)/*.java $(GUI_DIR)/*.java $(MAIN_CLASS).java)

# Target for Compile
all: $(BIN_DIR) $(BIN_DIR)/$(MAIN_CLASS).jar

# Compile Java files
$(BIN_DIR):
	-@mkdir $(BIN_DIR)
	$(JAVAC) -d $(BIN_DIR) $(SOURCES)

# Create Jar file
$(BIN_DIR)/$(MAIN_CLASS).jar: $(BIN_DIR) $(SOURCES)
	@echo "Creating JAR file..."
	$(JAR) cfe $(BIN_DIR)/$(MAIN_CLASS).jar $(MAIN_CLASS) -C $(BIN_DIR) .

# Run Game
run: $(BIN_DIR)/$(MAIN_CLASS).jar
	$(JAVA) -jar $(BIN_DIR)/$(MAIN_CLASS).jar

# Clean All Files
clean:
	-@rmdir /S /Q $(BIN_DIR) || rm -rf $(BIN_DIR)

re: clean all

.PHONY: all run clean re
