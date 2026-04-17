from ollama import Client
import logging

client = Client()
logger = logging.getLogger("apex.owen")

SYSTEM_PROMPT = """You are an expert coding agent.

Rules:
1. Analyze requirements and edge cases before coding.
2. Write clean, production-ready code.
3. Follow best practices.
4. State assumptions briefly if needed.

Output:
- Start with code (markdown fenced)
- Add "Notes" only if necessary
- No unnecessary text
"""

def generate(task_text: str) -> str:
    try:
        response = client.chat(
            model="qwen2.5-coder:3b",
            messages=[{"role": "system", "content": SYSTEM_PROMPT},{"role": "user", "content": task_text}],
            options={"temperature": 0.1,"top_p": 0.9,"repeat_penalty": 1.1,"num_predict": 1000,"stop": ["\n\n\n"]},keep_alive=30)
        text = response["message"]["content"]
        if not text or len(text.strip()) < 20:
            raise ValueError("Empty response")
        logger.info("owen_success | len=%s", len(text))
        return text
    except Exception as e:
        logger.warning("owen_fallback | error=%s", e)
        return client.chat(model="phi3:mini",messages=[{"role": "user", "content": task_text}])["message"]["content"]