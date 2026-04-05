from fastapi import FastAPI
from pydantic import BaseModel

try:
    from .core import CodeAnalyzer
except ImportError:
    from core import CodeAnalyzer

app = FastAPI()
analyzer = CodeAnalyzer()

class CodeRequest(BaseModel):
    code: str

@app.get("/")
def home():
    return {"status": "running"}

@app.post("/analyze")
def analyze(req: CodeRequest):
    return analyzer.analyze(req.code)
