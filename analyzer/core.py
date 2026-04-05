import ast
try:
    from .visitors.loop import LoopVisitor
    from .visitors.function import FunctionVisitor
    from .visitors.recursion import RecursionVisitor
    from .visitors.if_visitor import IfVisitor
    from .visitors.class_visitor import ClassVisitor
except ImportError:
    from visitors.loop import LoopVisitor
    from visitors.function import FunctionVisitor
    from visitors.recursion import RecursionVisitor
    from visitors.if_visitor import IfVisitor
    from visitors.class_visitor import ClassVisitor

class CodeAnalyzer:
    def analyze(self, code: str):
        result = {
            "language": "Python",
            "lines": 0,
            "words": 0,
            "ifCount": 0,
            "loopCount": 0,
            "functionCount": 0,
            "classCount": 0,
            "timeComplexity": "O(1)",
            "spaceComplexity": "O(1)",
            "cyclomaticComplexity": "Low",
            "maxNestingDepth": 0,
        }

        if not code or not code.strip():
            return result

        result["lines"] = len(code.splitlines())
        result["words"] = len(code.split())

        try:
            tree = ast.parse(code)
        except Exception:
            result["timeComplexity"] = "N/A"
            result["spaceComplexity"] = "N/A"
            result["cyclomaticComplexity"] = "N/A"
            return result
        
        loop_v = LoopVisitor()
        func_v = FunctionVisitor()
        rec_v = RecursionVisitor()
        if_v = IfVisitor()
        class_v = ClassVisitor()

        if_v.visit(tree)
        class_v.visit(tree)
        loop_v.visit(tree)
        func_v.visit(tree)
        rec_v.visit(tree)

        time_complexity = self._time_complexity(loop_v.max_depth)
        space_complexity = self._space_complexity(tree)
        cyclomatic_score = 1 + if_v.count + loop_v.loop_count + (1 if rec_v.recursive else 0)

        result["ifCount"] = if_v.count
        result["loopCount"] = loop_v.loop_count
        result["functionCount"] = func_v.count
        result["classCount"] = class_v.count
        result["timeComplexity"] = time_complexity
        result["spaceComplexity"] = space_complexity
        result["cyclomaticComplexity"] = self._complexity_level(cyclomatic_score)
        result["maxNestingDepth"] = loop_v.max_depth

        return result
    
    def _time_complexity(self, depth: int):
        if depth == 0:
            return "O(1)"
        elif depth == 1:
            return "O(n)"
        else:
            return f"O(n^{depth})"

    def _space_complexity(self, tree):
        for node in ast.walk(tree):
            if isinstance(node, ast.List):
                return "O(n)"
        return "O(1)"

    def _complexity_level(self, score: int):
        if score <= 5:
            return "Low"
        elif score <= 10:
            return "Moderate"
        return "High"
