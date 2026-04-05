const particleCount = 40;
const particles = [];

for (let i = 0; i < particleCount; i++) {
    const particle = document.createElement('div');
    particle.className = 'particle';

    const size = Math.random() * 8 + 4;
    particle.style.width = size + 'px';
    particle.style.height = size + 'px';

    const colors = ['cyan', 'white', 'lime'];
    particle.style.background = colors[Math.floor(Math.random() * colors.length)];

    let x = Math.random() * window.innerWidth;
    let y = Math.random() * window.innerHeight;

    let vx = (Math.random() - 0.5) * 1.2;
    let vy = (Math.random() - 0.5) * 1.2;

    particle.style.left = x + 'px';
    particle.style.top = y + 'px';

    document.body.appendChild(particle);
    particles.push({ particle, x, y, vx, vy, size });
}

function animate() {
    for (const p of particles) {
        p.x += p.vx;
        p.y += p.vy;
        if (p.x <= 0 || p.x >= window.innerWidth - p.size) {
            p.vx *= -1;
        }
        if (p.y <= 0 || p.y >= window.innerHeight - p.size) {
            p.vy *= -1;
        }
        p.particle.style.left = p.x + 'px';
        p.particle.style.top = p.y + 'px';
    }
    requestAnimationFrame(animate);
}
animate();
