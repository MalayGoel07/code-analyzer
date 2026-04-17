from fastapi import FastAPI
from pydantic import BaseModel
from owen_agent import generate

try:
    from .core import CodeAnalyzer
except ImportError:
    from core import CodeAnalyzer

app = FastAPI()
analyzer = CodeAnalyzer()

class CodeRequest(BaseModel):
    code: str

class Request(BaseModel):
    task: str

@app.post("/ai/generate")
def ai_generate(req: Request):
    return {"response": generate(req.task)}

@app.get("/")
def home():
    return {"status": "running"}

@app.post("/analyze")
def analyze(req: CodeRequest):
    return analyzer.analyze(req.code)
