# ตัวแปรที่ใช้
JAVAC = javac
JAVA = java
SRC_DIR = Source
GUI_DIR = GUI
BIN_DIR = bin
MAIN_CLASS = Main.java

# ค้นหาทุกไฟล์ .java ใน SRC_DIR และ GUI_DIR
SOURCES = $(wildcard $(SRC_DIR)/*.java $(GUI_DIR)/*.java)

# Target สำหรับคอมไพล์โค้ด
all: $(BIN_DIR)/$(MAIN_CLASS).class

$(BIN_DIR)/$(MAIN_CLASS).class: $(SOURCES) | $(BIN_DIR)
	$(JAVAC) -d $(BIN_DIR) $(SOURCES)

# สร้างโฟลเดอร์ bin ถ้ายังไม่มี
$(BIN_DIR):
	mkdir $(BIN_DIR)

# รันเกม
run: all
	$(JAVA) -cp $(BIN_DIR) $(MAIN_CLASS)

# ลบไฟล์ที่คอมไพล์แล้ว
clean:
	-@rmdir /S /Q $(BIN_DIR) 2>nul || rm -rf $(BIN_DIR)
