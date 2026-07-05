# AutoTotem - Fabric Mod (Minecraft 1.21.1 / Fabric API 26.1.2)

> **อัปเดต:** แก้โครงสร้างไฟล์ให้ถูกต้องแล้ว (ย้ายโค้ดไป `src/client/java` ตามที่
> Fabric ต้องการสำหรับโค้ด client-only) คุณไม่ต้องแก้อะไรเองอีก แค่ทำตาม
> "วิธีอัปเดต repo เดิมให้ถูกต้อง" ด้านล่างนี้

## วิธีอัปเดต repo เดิม (nontify007/FULL-OPTISION) ให้ถูกต้อง

วิธีที่ง่ายและชัวร์ที่สุดคือ **ลบไฟล์ทั้งหมดออกจาก repo เดิมแล้วอัปโหลดใหม่ทั้งชุด**
แทนที่จะไปไล่แก้ทีละไฟล์ (จะได้ไม่งงเหมือนรอบที่แล้ว)

1. เข้า repo `nontify007/FULL-OPTISION` บน GitHub
2. ลบไฟล์/โฟลเดอร์เดิมทั้งหมดออกก่อน **ยกเว้นโฟลเดอร์ `.github`** (ถ้ามี workflow ที่แก้ไว้แล้วอยู่ ปล่อยไว้ได้ หรือจะลบทิ้งแล้วใช้ของใหม่ในซิปนี้แทนก็ได้ เพราะซิปนี้มี `.github/workflows/build.yml` มาให้พร้อมแล้ว)
   - วิธีลบไว: เข้าไปทีละไฟล์/โฟลเดอร์ที่หน้า repo → เปิดไฟล์ → กด `...` → Delete file → Commit
   - หรือง่ายกว่านั้น: ลบ repo เดิมทิ้งไปเลย (Settings ล่างสุด > Delete this repository)
     แล้วสร้าง repo ใหม่ชื่อเดิมหรือชื่อใหม่ก็ได้ จะได้เริ่มจากศูนย์ สะอาดกว่า
3. แตกไฟล์ zip **อันใหม่**นี้ในเครื่อง จะได้โฟลเดอร์ `autototem-mod`
4. ที่หน้า repo (ใหม่หรือที่ล้างแล้ว) กด **Add file → Upload files**
5. **ลากทุกอย่างที่อยู่ข้างในโฟลเดอร์ `autototem-mod`** (ไม่ใช่ลากตัวโฟลเดอร์ `autototem-mod`
   เอง แต่ลากไฟล์/โฟลเดอร์ย่อยข้างในมันทั้งหมดพร้อมกัน) เข้าไปที่หน้าอัปโหลด
   - ให้แน่ใจว่าลาก **โฟลเดอร์ `.github` และ `src` ไปด้วย** (เป็นโฟลเดอร์ที่ชื่อขึ้นต้นด้วยจุด
     กับตัวโค้ดหลัก มักจะถูกลืมลาก)
   - เช็คให้ได้โครงสร้างแบบนี้ที่ root ของ repo: `build.gradle`, `settings.gradle`,
     `gradle.properties`, `README.md`, โฟลเดอร์ `.github`, โฟลเดอร์ `src`
6. เลื่อนลงล่าง กด **Commit changes**
7. ไปแท็บ **Actions** → รอ build อัตโนมัติ (หรือกด Run workflow ถ้าไม่เริ่มเอง) รอเขียว ✅
8. ดาวน์โหลด `.jar` จากช่อง **Artifacts**

ครั้งนี้ควร build ผ่านครับ เพราะไฟล์โค้ดถูกย้ายไปอยู่ที่ `src/client/java/...`
แล้ว (แก้ไว้ในซิปเรียบร้อย) และ `build.gradle` มีการเปิดใช้
`splitEnvironmentSourceSets()` ให้ตรงกับโครงสร้างนี้แล้วด้วย

ถ้ายัง error ให้ส่ง log หน้า Actions มาดูได้เลยครับ

---

มอด client-side: เมื่อเปิดกระเป๋า (กด E) ถ้าช่อง offhand ไม่มี Totem of Undying
ระบบจะหา Totem ในกระเป๋าแล้วสลับเข้าไปที่ offhand ให้อัตโนมัติ (ของเดิมใน offhand
จะถูกย้ายไปอยู่ที่ช่องที่ totem เคยอยู่)

**ปุ่มควบคุม (เปลี่ยนได้ในเกม: Options > Controls > AutoTotem):**
- `Right Shift` = เปิด/ปิดมอดทั้งหมด (มีข้อความแจ้งที่ action bar)
- `Right Control` = สลับโหมด
  - โหมด "On inventory open" (ค่าเริ่มต้น) = ทำงานเฉพาะตอนเปิดกระเป๋าเท่านั้น (ตรงกับที่ขอ)
  - โหมด "Always" = ทำงานตลอดเวลา แม้ไม่ได้เปิดกระเป๋า (ยกเว้นตอนเปิดกล่อง/เตาเผา ฯลฯ)

การสลับไอเทมใช้แพ็กเก็ต click-slot จริง เหมือนเราคลิกเมาส์ลากไอเทมเอง จึงซิงค์กับ
เซิร์ฟเวอร์ถูกต้อง ไม่ใช่การโกงแบบแก้ข้อมูลฝั่ง client เฉยๆ

---

## วิธีที่ง่ายที่สุด: ให้ GitHub คอมไพล์ให้ (ไม่ต้องลงอะไรในเครื่องเลย)

ถ้าไม่อยากติดตั้ง Java/Gradle เอง วิธีนี้ง่ายสุด แค่มีบัญชี GitHub (ฟรี)

1. เข้า https://github.com แล้วสร้าง repository ใหม่ (New repository) ตั้งชื่ออะไรก็ได้
   เช่น `autototem-mod` — เลือก **Public** หรือ **Private** ก็ได้ ไม่ต้องติ๊ก "Add README"

2. ในหน้า repository ที่สร้างเสร็จ กด **"uploading an existing file"**
   (หรือปุ่ม Add file > Upload files)

3. แตกไฟล์ zip นี้ในเครื่องออกมาก่อน แล้ว**ลากทั้งโฟลเดอร์ `autototem-mod` ทั้งหมด**
   (รวมโฟลเดอร์ `.github` ด้วย — ถ้า GitHub เว็บไม่ยอมลากทั้งโฟลเดอร์ ให้ลากทีละไฟล์/โฟลเดอร์ย่อยแทน
   ต้องได้โครงสร้างเหมือนในซิปเป๊ะๆ คือมีไฟล์ `build.gradle` อยู่ที่ root ของ repo)

4. กด **Commit changes** ที่ด้านล่าง

5. ไปที่แท็บ **Actions** ด้านบนของหน้า repository
   - ระบบจะเริ่ม build ให้อัตโนมัติทันที (จะเห็นสถานะเป็นวงกลมหมุนๆ สีเหลือง)
   - ถ้าไม่เริ่มเอง ให้กด workflow ชื่อ "Build Mod" ทางซ้าย แล้วกด **Run workflow**
   - รอประมาณ 2-5 นาที จนสถานะเปลี่ยนเป็นเครื่องหมายถูกสีเขียว ✅

6. กดเข้าไปใน run ที่เสร็จแล้ว (อันที่มีเครื่องหมายถูกเขียว) เลื่อนลงมาล่างสุดจะเห็นหัวข้อ
   **Artifacts** มีไฟล์ชื่อ `autototem-jar` ให้กดดาวน์โหลด (จะได้เป็น .zip ข้างในมี .jar อีกที)

7. แตกซิปนั้น จะได้ไฟล์ `.jar` เอาไปใส่โฟลเดอร์ `mods` ของ Minecraft ได้เลย
   (**ใช้ไฟล์ที่ชื่อไม่มีคำว่า `-sources` ต่อท้าย**)

**หมายเหตุ:** วิธีนี้ GitHub เป็นคนรัน `gradle build` ให้บนเซิร์ฟเวอร์ของเขา ไม่ได้ยุ่งกับ
เครื่องคุณเลย จึงไม่ต้องกังวลเรื่องลง Java/Gradle ผิดเวอร์ชัน — ถ้า build fail (กากบาทแดง)
ให้กดดู log ในหน้า Actions จะบอกว่า error ตรงไหน ส่งข้อความ error มาให้ผมดูได้เลยครับ
ผมช่วยแก้ให้ได้

---

## วิธีที่สอง (ถ้าอยากลงมือทำเอง): ใช้ตัวสร้างโปรเจกต์ทางการของ Fabric

เวอร์ชันของ Gradle/Loom/Fabric API เปลี่ยนบ่อยมาก การพิมพ์เลขเวอร์ชันเองมีโอกาส
ผิดพลาด วิธีที่ชัวร์สุดคือให้ตัวสร้างโปรเจกต์ทางการช่วยตั้งค่าให้ถูกต้องเสมอ แล้ว
ค่อยนำไฟล์โค้ดของมอดนี้ (3 ไฟล์) ไปแปะทับ

### ขั้นตอน

1. ต้องติดตั้งก่อน:
   - **Java Development Kit (JDK) 21** (เช่น Temurin 21 จาก https://adoptium.net)
   - ไม่ต้องติดตั้ง Gradle เอง (ตัวโปรเจกต์จะมี `gradlew` ให้ใช้)

2. เข้าไปที่ https://fabricmc.net/develop/template/
   - เลือก Minecraft version: `1.21.1`
   - Mod name: `AutoTotem`
   - Package name: `com.example.autototem`
   - ติ๊ก "Use Fabric API" ✅
   - กด Generate/Download แล้วแตกซิปไฟล์ที่ได้ออกมา -> จะได้โฟลเดอร์โปรเจกต์ที่มี
     `gradlew`, `gradlew.bat`, `build.gradle`, `gradle.properties` ที่ตั้งค่าเวอร์ชันถูกต้องพร้อมใช้แล้ว

3. เอาไฟล์จากมอดนี้ไปแทนที่/เพิ่มในโปรเจกต์ที่เพิ่งสร้าง:
   - คัดลอกไฟล์
     `src/client/java/com/example/autototem/AutoTotemModClient.java`
     ไปวางที่ตำแหน่งเดียวกันในโปรเจกต์ใหม่ (ต้องอยู่ใต้ `src/client/java/...` ไม่ใช่ `src/main/java/...`
     เพราะเป็นโค้ด client-only — ถ้า generator สร้างไฟล์ตัวอย่างชื่อ `ExampleModClient.java`
     ไว้ที่ `src/client/java/...` อยู่แล้ว ให้ลบไฟล์ตัวอย่างนั้นทิ้งแล้ววางไฟล์ของเราแทน)
   - เปิดไฟล์ `fabric.mod.json` ของโปรเจกต์ใหม่ (อยู่ที่ `src/main/resources/fabric.mod.json`)
     แล้วแก้ส่วน `"entrypoints"` ให้มี client entrypoint ชี้ไปที่คลาสของเรา:
     ```json
     "entrypoints": {
       "client": [
         "com.example.autototem.AutoTotemModClient"
       ]
     },
     ```
     และตั้งค่า `"environment": "client"` เพราะมอดนี้เป็น client-only
     (จะคัดลอกทั้งไฟล์ `fabric.mod.json` ที่แนบมาในซิปนี้ไปทับเลยก็ได้ ถ้า id/ชื่อ mod ตรงกัน)
   - คัดลอกไฟล์ `src/main/resources/assets/autototem/lang/en_us.json` ไปวางที่ตำแหน่งเดียวกัน
     (สร้างโฟลเดอร์ `assets/autototem/lang/` เองถ้ายังไม่มี)

4. เปิด Terminal/CMD ที่โฟลเดอร์โปรเจกต์ แล้วรัน:
   - Windows: `gradlew.bat build`
   - macOS/Linux: `./gradlew build`

5. รอ Gradle โหลดไลบรารีครั้งแรก (อาจใช้เวลาสักพัก) พอเสร็จจะได้ไฟล์ `.jar`
   อยู่ที่ `build/libs/autototem-1.0.0.jar` (ชื่ออาจต่างกันเล็กน้อยตามที่ตั้งไว้ตอน generate)
   **ให้ใช้ไฟล์ที่ไม่มีคำว่า `-sources` หรือ `-dev` ต่อท้าย**

6. เอาไฟล์ `.jar` นั้นไปใส่ในโฟลเดอร์ `mods` ของ Minecraft (ต้องติดตั้ง Fabric Loader +
   Fabric API เวอร์ชันที่ตรงกับ Minecraft 1.21.1 ไว้ในเกมด้วย ถ้ายังไม่มี Fabric API
   ให้โหลดจาก https://modrinth.com/mod/fabric-api มาใส่โฟลเดอร์ mods เช่นกัน)

7. เปิดเกม เลือกโปรไฟล์ Fabric แล้วเข้าเกมได้เลย

---

## วิธีที่สาม: ใช้ไฟล์โปรเจกต์ที่แนบมาในซิปนี้ตรงๆ (ทำเองในเครื่อง)

ในซิปนี้มีโครงโปรเจกต์ Gradle มาให้ครบแล้ว (`build.gradle`, `settings.gradle`,
`gradle.properties`, ซอร์สโค้ด) **แต่ไม่ได้แนบ Gradle Wrapper (`gradlew`) มาด้วย**
เพราะเป็นไฟล์ไบนารีที่ต้องดาวน์โหลดจากอินเทอร์เน็ต ถ้าคุณมี Gradle ติดตั้งในเครื่อง
อยู่แล้ว (หรือจะสร้าง wrapper เอง) ทำตามนี้ได้:

1. ติดตั้ง JDK 21 และ Gradle (เช่นผ่าน https://gradle.org/install/ หรือ SDKMAN)
2. แตกไฟล์ zip นี้ออกมา เปิด Terminal ที่โฟลเดอร์ `autototem-mod`
3. รัน `gradle wrapper` หนึ่งครั้ง (สร้างไฟล์ `gradlew` ให้ใช้ครั้งต่อไปได้)
4. รัน `./gradlew build` (หรือ `gradlew.bat build` บน Windows)
5. ถ้า build error บอกว่าหาเวอร์ชันไม่เจอ (Fabric API / Loader เวอร์ชันอาจ
   เปลี่ยนไปตั้งแต่เขียนไฟล์นี้) ให้เข้าไปเช็คเวอร์ชันล่าสุดที่ตรงกับ Minecraft 1.21.1
   ที่ https://modrinth.com/mod/fabric-api/versions แล้วแก้ค่า `fabric_version`
   ใน `gradle.properties` ให้ตรง
6. ไฟล์ .jar ที่ได้จะอยู่ที่ `build/libs/`

**หมายเหตุ:** วิธีนี้เสี่ยง build fail ได้ง่ายกว่าวิธีแรก เพราะเลขเวอร์ชันของ
Fabric Loom/Loader/API เปลี่ยนบ่อย แนะนำให้ใช้วิธีแรก (official template
generator) ถ้าไม่อยากเสียเวลาแก้เวอร์ชันเอง

---

## ไฟล์ในซิปนี้

```
autototem-mod/
├── .github/workflows/build.yml   (สคริปต์ build อัตโนมัติบน GitHub Actions)
├── build.gradle
├── settings.gradle
├── gradle.properties
├── README.md   (ไฟล์นี้)
└── src/
    ├── client/java/com/example/autototem/AutoTotemModClient.java   <- โค้ดหลักของมอด (client-only)
    └── main/resources/
        ├── fabric.mod.json
        └── assets/autototem/lang/en_us.json
```

## ปรับแต่งเพิ่มเติม

- อยากเปลี่ยนปุ่มเริ่มต้น: แก้ `GLFW.GLFW_KEY_RIGHT_SHIFT` และ
  `GLFW.GLFW_KEY_RIGHT_CONTROL` ในไฟล์ `AutoTotemModClient.java`
- อยากให้เริ่มเกมมาแล้ว "Always mode" เป็นค่าเริ่มต้นเลย: แก้บรรทัด
  `private static Mode mode = Mode.ON_INVENTORY_OPEN;` เป็น `Mode.ALWAYS`
- อยากให้ mod เปิดใช้งานอัตโนมัติทันทีที่เข้าเกม (ค่าเริ่มต้นเป็น ON อยู่แล้ว):
  แก้ `private static boolean enabled = true;` เป็น `false` ถ้าอยากให้เริ่มแบบปิดไว้ก่อน

## ข้อควรทราบ

- นี่คือมอด client-side ต้องลง Fabric API ในเกมด้วยเสมอ (mod นี้ depend on fabric-api)
- ถ้าเล่นในเซิร์ฟเวอร์ที่ไม่อนุญาตให้ใช้มอดประเภทนี้ (ถือเป็นการช่วยเล่นอัตโนมัติ)
  ควรตรวจสอบกฎของเซิร์ฟเวอร์ก่อนใช้งาน เซิร์ฟเวอร์บางแห่งอาจแบนผู้เล่นที่ใช้มอด
  ลักษณะนี้เพราะถือว่าได้เปรียบผู้เล่นคนอื่น
