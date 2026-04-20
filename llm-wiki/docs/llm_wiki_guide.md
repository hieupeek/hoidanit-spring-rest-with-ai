# Hướng dẫn xây LLM Wiki chuẩn chỉ

> Dành cho project: `hoidanit-spring-rest-with-ai`

---

## Tổng quan: Wiki hiện tại ở đâu?

```
llm-wiki/
├── CLAUDE.md          ✅ Schema đã có — hướng dẫn AI vận hành wiki
├── llm-wiki.md        ✅ Tài liệu ý tưởng gốc (đọc để hiểu)
├── raw/               ❌ Trống — cần thả tài liệu vào đây
├── wiki/
│   ├── index.md       ✅ Khung đã có — nhưng 0 trang
│   └── log.md         ✅ Đã init — nhưng chưa có gì
└── templates/         (chưa khảo sát)
```

**Vấn đề hiện tại:** Cơ sở hạ tầng đã đủ, nhưng **`raw/` trống** — wiki không có gì để "ăn" vào. Đây là bước đầu tiên cần làm.

---

## Workflow chuẩn: 3 giai đoạn

```
[1] SETUP          →    [2] INGEST         →    [3] USE
Chuẩn bị raw/          Thả doc vào + AI         Query wiki +
                        tổng hợp vào wiki/       Lint định kỳ
```

---

## Giai đoạn 1 — SETUP (Làm một lần)

### Bước 1.1: Tạo folder structure trong `raw/`

```
raw/
├── api-specs/       ← Swagger/OpenAPI specs, Postman collections
├── architecture/    ← ADR (Architecture Decision Records), diagrams
├── database/        ← Schema SQL, ERD, migration files
├── security/        ← JWT docs, Spring Security config notes
├── lessons/         ← Bugs đã fix, gotchas, lessons learned
└── assets/          ← Ảnh, diagrams (download về local)
```

Tạo bằng lệnh:
```bash
mkdir -p raw/api-specs raw/architecture raw/database raw/security raw/lessons raw/assets
```

### Bước 1.2: Chuẩn bị CLAUDE.md như một "system prompt"

File `CLAUDE.md` đã có rồi. Cách dùng: **Mỗi khi mở session mới với AI**, paste nội dung `CLAUDE.md` vào đầu cuộc trò chuyện, hoặc để Claude Code tự đọc nó (Claude Code tự động đọc file `CLAUDE.md` trong project root).

> [!IMPORTANT]
> Nếu dùng **Claude Code**, chuyển `CLAUDE.md` từ `llm-wiki/` lên thư mục gốc của project, hoặc import nó. Claude Code tự động đọc `CLAUDE.md` ở root.
> Nếu dùng **AI khác** (ChatGPT, Gemini...), paste nội dung `CLAUDE.md` vào đầu session.

---

## Giai đoạn 2 — INGEST (Làm với từng tài liệu)

### Bước 2.1: Thu thập tài liệu nguồn cho `raw/`

Những thứ bạn nên thả vào `raw/` cho project Spring REST API:

| Tài liệu | Để vào | Lấy từ đâu |
|---|---|---|
| OpenAPI/Swagger spec | `raw/api-specs/` | Export từ Swagger UI hoặc file `.yaml` |
| Database schema | `raw/database/` | Export SQL từ MySQL Workbench hoặc chụp ERD |
| Spring Security config | `raw/security/` | Copy file `SecurityConfig.java` + ghi chú |
| JWT flow notes | `raw/security/` | Tự viết hoặc copy từ docs |
| Architecture decisions | `raw/architecture/` | Viết ngắn: "Tại sao dùng JWT thay vì session?" |
| Bug reports đã fix | `raw/lessons/` | Ghi lại các bug hay gặp + cách fix |

### Bước 2.2: Quy trình ingest 1 tài liệu (lặp lại cho từng doc)

**Khi bạn thả một file mới vào `raw/`, bảo AI:**

```
Tôi vừa thêm file [tên file] vào raw/[folder]/.
Hãy ingest nó theo CLAUDE.md:
1. Đọc file đó
2. Thảo luận các điểm kỹ thuật quan trọng với tôi
3. Tạo summary page trong wiki/
4. Tạo/cập nhật concept pages liên quan
5. Cập nhật wiki/index.md
6. Append vào wiki/log.md
```

> [!TIP]
> Ingest **từng file một**, đừng batch nhiều file cùng lúc. Như vậy bạn kiểm soát được chất lượng từng trang wiki.

### Bước 2.3: Cấu trúc mỗi trang wiki (AI tự làm, bạn cần biết để review)

```markdown
# Tên Concept

**Concept**: 1-2 câu mô tả đây là gì.
**Related Skills**: [skill liên quan nếu có]
**Sources**: [file raw/ nguồn]
**Last updated**: 2026-04-20

---

## Detailed Explanation
Nội dung chính...

## Architecture / Implementation Notes
Code, diagram, constraints...

## Related concepts
- [[related-concept-1]]
- [[related-concept-2]]
```

---

## Giai đoạn 3 — USE (Dùng hàng ngày)

### Query wiki

Khi cần hỏi gì về project, bảo AI:
```
Đọc wiki/index.md và trả lời: [câu hỏi của bạn]
Cite cụ thể wiki page nào bạn đã dùng.
```

### Lint wiki (làm định kỳ, ví dụ mỗi tuần)

```
Audit wiki theo CLAUDE.md:
- Tìm orphan pages (không có inbound link)
- Tìm concepts được mention nhưng chưa có page
- Kiểm tra contradictions giữa các pages
- Báo cáo gaps cần bổ sung
```

### File good answers back vào wiki

Khi AI trả lời hay → bảo nó lưu lại:
```
Câu trả lời này valuable. Hãy lưu nó thành wiki page mới.
```

---

## Road map cụ thể cho bạn — Làm theo thứ tự này

### Tuần 1: Nền móng
- [ ] `mkdir` folder structure trong `raw/`
- [ ] Thu thập ít nhất 3 tài liệu nguồn: OpenAPI spec, DB schema, JWT flow
- [ ] Ingest từng cái → wiki có ~5-10 trang đầu tiên

### Tuần 2: Mở rộng
- [ ] Thêm `raw/lessons/` với các bug đã gặp
- [ ] Ingest thêm adr/architecture notes
- [ ] Chạy lần lint đầu tiên

### Ongoing: Duy trì
- [ ] Mỗi khi build tính năng mới → thêm note vào `raw/lessons/`
- [ ] Mỗi khi fix bug quan trọng → ingest vào wiki
- [ ] Mỗi sprint → chạy lint một lần

---

## Công cụ hỗ trợ (optional nhưng nên dùng)

| Tool | Dùng để | Link |
|---|---|---|
| **Obsidian** | Duyệt wiki, xem graph view, follow links | Đã có trong project (`.obsidian/`) |
| **Obsidian Web Clipper** | Clip bài viết từ web → markdown vào `raw/` | Browser extension |
| **Git** | Version history cho wiki — track thay đổi theo thời gian | `git init` trong `llm-wiki/` |

> [!NOTE]
> Thư mục `llm-wiki/` đã là một Obsidian vault (có `.obsidian/`). Bạn chỉ cần mở Obsidian → Open folder → chọn `llm-wiki/` là dùng được ngay.

---

## Những sai lầm hay gặp

| Sai lầm | Hậu quả | Cách tránh |
|---|---|---|
| Ingest nhiều file cùng lúc | Wiki loãng, thiếu depth | Ingest 1 file, review, rồi mới ingest tiếp |
| Quên update index.md | AI không tìm được trang → answer kém | CLAUDE.md đã nhắc, nhưng bạn nên kiểm tra thủ công |
| Không file answers back | Knowledge biến mất vào chat history | Thói quen: mỗi answer hay → lưu lại |
| Sửa file trong `raw/` | Mất source of truth | `raw/` là immutable — KHÔNG sửa |
| Wiki quá chung chung | Không có value | Focus vào project-specific knowledge, không copy docs chung |
