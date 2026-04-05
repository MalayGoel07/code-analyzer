import ast

class RecursionVisitor(ast.NodeVisitor):
    def __init__(self):
        self.current_function = None
        self.recursive = False

    def visit_FunctionDef(self, node):
        self.current_function = node.name
        self.generic_visit(node)
        self.current_function = None

    def visit_Call(self, node):
        if isinstance(node.func, ast.Name):
            if node.func.id == self.current_function:
                self.recursive = True
        self.generic_visit(node)